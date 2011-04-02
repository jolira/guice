/**
 * (C) 2010 jolira (http://www.jolira.com). Licensed under the GNU General
 * Public License, Version 3.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/gpl-3.0-standalone.html Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.google.code.joliratools.plugins;

import static com.google.inject.Stage.DEVELOPMENT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.Stage;

/**
 * This class is the core of the plugin mechanism. It searches all available jar
 * files for manifest files with a {@literal Guice-Module} directive,
 * instantiates the module identified by this directive, and constructs an
 * {@link Injector} based on these modules.
 * 
 * @author jfk
 */
public class PluginManager {
    private static final Logger LOG = LoggerFactory.getLogger(PluginManager.class);
    private static final String NAME = "com.google.inject.Module";
    private static final String MOCK_NAME = "com.google.inject.MockModule";
    private static final String MOCK_SERVICE_ID = "META-INF/services/" + MOCK_NAME;
    private static final String SERVICE_ID = "META-INF/services/" + NAME;

    private static void close(final Reader reader) {
        try {
            reader.close();
        } catch (final IOException e) {
            throw new PluginException(e);
        }
    }

    private static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            @Override
            public ClassLoader run() {
                final Thread thread = Thread.currentThread();

                try {
                    return thread.getContextClassLoader();
                } catch (final SecurityException e) {
                    throw new PluginException(e);
                }
            }
        });
    }

    private static URL[] getServiceURLs(final boolean loadMocks) {
        final Enumeration<URL> clUrls = loadFromClasLoader(loadMocks);
        final Collection<URL> result = new HashSet<URL>();

        while (clUrls.hasMoreElements()) {
            final URL url = clUrls.nextElement();

            result.add(url);
        }

        loadFromProperties(result);

        final int size = result.size();

        return result.toArray(new URL[size]);
    }

    private static URL getURL(final String url) {
        final String normalized = normalize(url);

        try {
            return new URL(normalized);
        } catch (final MalformedURLException e) {
            throw new PluginException(e);
        }
    }

    private static Enumeration<URL> loadFromClasLoader(final boolean loadMocks) {
        final ClassLoader cl = getContextClassLoader();

        try {
            return cl.getResources(loadMocks ? MOCK_SERVICE_ID : SERVICE_ID);
        } catch (final IOException e) {
            throw new PluginException(e);
        }
    }

    private static void loadFromProperties(final Collection<URL> result) {
        final String vals = System.getProperty(NAME);

        if (vals == null) {
            return;
        }

        final String[] split = vals.split(";");

        for (final String _url : split) {
            final URL url = getURL(_url);

            result.add(url);
        }
    }

    private static void loadModules(final URL url, final Collection<Module> modules,
            final Collection<Class<? extends Module>> loaded) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(open(url)));

        try {
            for (;;) {
                final String line = reader.readLine();

                if (line == null) {
                    return;
                }

                final String className = line.trim();

                if (className.isEmpty()) {
                    continue;
                }

                final Class<?> cls = Class.forName(className);
                final Module module = (Module) cls.newInstance();
                final Class<? extends Module> clazz = module.getClass();

                if (loaded.add(clazz)) {
                    modules.add(module);
                    LOG.info("added module " + clazz + " from " + url);
                } else {
                    LOG.warn("module " + clazz + " from " + url + " has already been added");
                }
            }
        } catch (final IOException e) {
            throw new PluginException(e);
        } catch (final ClassNotFoundException e) {
            throw new PluginException(e);
        } catch (final InstantiationException e) {
            throw new PluginException(e);
        } catch (final IllegalAccessException e) {
            throw new PluginException(e);
        } finally {
            close(reader);
        }
    }

    private static String normalize(final String url) {
        if (url.endsWith(NAME)) {
            return url;
        }

        if (url.endsWith("/")) {
            return url + SERVICE_ID;
        }

        return url + '/' + SERVICE_ID;
    }

    private static InputStream open(final URL url) {
        try {
            return url.openStream();
        } catch (final IOException e) {
            throw new PluginException(e);
        }
    }

    private Injector cachedInjector = null;
    private final Stage stage;

    private final boolean loadMocks;
    private final Module[] modules;

    /**
     * Create an new plugin manager that creates {@literal DEVLOPMENT} stage
     * {@link Injector}s.
     * 
     * @param loadMocks
     *            if set to {@literal true} modules are searched in
     *            {@literal "META-INF/services/com.google.inject.MockModule"};
     *            if set to {@literal false}
     *            {@literal "META-INF/services/com.google.inject.Module"} is
     *            searched.
     * @param modules
     *            optional modules that should be made available to the injector
     */
    public PluginManager(final boolean loadMocks, final Module... modules) {
        this(DEVELOPMENT, loadMocks, modules);
    }

    /**
     * Create an new plugin manager that creates {@literal DEVLOPMENT} stage
     * {@link Injector}s.
     * 
     * @param modules
     *            optional modules that should be made available to the injector
     */
    public PluginManager(final Module... modules) {
        this(DEVELOPMENT, modules);
    }

    /**
     * Create an new plugin manager that creates {@link Injector}s of a
     * specified stage.
     * 
     * @param loadMocks
     *            if set to {@literal true} modules are searched in
     *            {@literal "META-INF/services/com.google.inject.MockModule"};
     *            if set to {@literal false}
     *            {@literal "META-INF/services/com.google.inject.Module"} is
     *            searched.
     * @param stage
     *            the stage to create
     * @param modules
     *            additional modules
     */
    public PluginManager(final Stage stage, final boolean loadMocks, final Module... modules) {
        this.stage = stage;
        this.loadMocks = loadMocks;
        this.modules = modules;
    }

    /**
     * Create an new plugin manager that creates {@link Injector}s of a
     * specified stage.
     * 
     * @param stage
     *            the stage to create
     * @param modules
     *            additional modules
     */
    public PluginManager(final Stage stage, final Module... modules) {
        this(stage, false, modules);
    }

    /**
     * Return a cached injector or create a new one.
     * 
     * @return the injector
     * @throws PluginException
     */
    public synchronized Injector getInjector() throws PluginException {
        if (cachedInjector != null) {
            return cachedInjector;
        }

        return cachedInjector = loadInjector();
    }

    private Injector loadInjector() {
        final URL[] urls = getServiceURLs(loadMocks);
        final Collection<Module> _modules = new ArrayList<Module>();
        final Scope scope = new PluginManagerScope();
        final Collection<Class<? extends Module>> loaded = new HashSet<Class<? extends Module>>();

        _modules.add(new Module() {
            @Override
            public void configure(final Binder binder) {
                binder.bindScope(ManagedSingleton.class, scope);
            }
        });

        for (final Module module : modules) {
            final Class<? extends Module> cls = module.getClass();

            if (loaded.add(cls)) {
                _modules.add(module);
            }
        }

        for (final URL url : urls) {
            loadModules(url, _modules, loaded);
        }

        return Guice.createInjector(stage, _modules);
    }
}
