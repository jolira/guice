/**
 * 
 */
package com.google.code.joliratools.plugins;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.inject.Injector;

/**
 * @author jfk
 * 
 */
public class PluginManagerTest {
    @Test
    public void testMain() {
        final PluginManager mgr = new PluginManager();
        final Injector injector = mgr.getInjector();

        assertNotNull(injector);
    }
}
