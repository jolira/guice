/**
 * (C) 2010 jolira (http://www.jolira.com). Licensed under the GNU General Public License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.gnu.org/licenses/gpl-3.0-standalone.html Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations
 * under the License.
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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.InjectorBuilder;
import com.google.inject.Module;
import com.google.inject.Scope;
import com.google.inject.Stage;

/**
 * This class is the core of the plugin mechanism. It searches all available jar files for manifest files with a
 * {@literal Guice-Module} directive, instantiates the module identified by this directive, and constructs an
 * {@link Injector} based on these modules.
 * 
 * @author jfk
 */
public class PluginManager {
    private static final String NAME = "com.google.inject.Module";
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

    private static URL[] getServiceURLs() {
        final Enumeration<URL> clUrls = loadFromClasLoader();
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

    private static Enumeration<URL> loadFromClasLoader() {
        final ClassLoader cl = getContextClassLoader();

        try {
            return cl.getResources(SERVICE_ID);
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

    private static void loadModule(final URL url, final InjectorBuilder builder, final Collection<Module> loaded) {
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

                if (module != null && loaded.add(module)) {
                    builder.addModules(module);
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

    private final Module[] modules;

    /**
     * Create an new plugin manager that creates {@literal DEVLOPMENT} stage {@link Injector}s.
     * 
     * @param modules
     *            optional modules that should be made available to the injector
     */
    public PluginManager(final Module... modules) {
        this(DEVELOPMENT, modules);
    }

    /**
     * Create an new plugin manager that creates {@link Injector}s of a specified stage.
     * 
     * @param stage
     *            the stage to create
     * @param modules
     *            additional modules
     */
    public PluginManager(final Stage stage, final Module... modules) {
        this.stage = stage;
        this.modules = modules;
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
        final URL[] urls = getServiceURLs();
        final InjectorBuilder builder = new InjectorBuilder();
        final Scope scope = new PluginManagerScope();
        final Collection<Module> loaded = new HashSet<Module>();

        builder.stage(stage);
        builder.addModules(new Module() {
            @Override
            public void configure(final Binder binder) {
                binder.bindScope(ManagedSingleton.class, scope);
            }
        });

        for (final Module module : modules) {
            if (loaded.add(module)) {
                builder.addModules(module);
            }
        }

        for (final URL url : urls) {
            loadModule(url, builder, loaded);
        }

        return builder.build();
    }
}
