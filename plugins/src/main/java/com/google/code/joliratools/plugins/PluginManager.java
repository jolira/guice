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

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import com.google.inject.Injector;

/**
 * This class is the core of the plugin mechanism. It searches all available jar
 * files for manifest files with a {@literal Guice-Module} directive,
 * instantiates the module identified by this directive, and constructs an
 * {@link Injector} based on these modules.
 * 
 * @author jfk
 * 
 */
public class PluginManager {
    private static Enumeration<URL> getManifestURLs() {
        final Thread tread = Thread.currentThread();
        final ClassLoader cl = tread.getContextClassLoader();

        try {
            return cl.getResources("/META-INF/MANIFEST.MF");
        } catch (final IOException e) {
            throw new PluginException(e);
        }
    }

    private Injector cachedInjector = null;

    public synchronized Injector getInjector() throws PluginException {
        if (cachedInjector != null) {
            return cachedInjector;
        }

        return cachedInjector = loadInjector();
    }

    private Injector loadInjector() {
        final Enumeration<URL> urls = getManifestURLs();

        while (urls.hasMoreElements()) {

        }

        return null;
    }
}
