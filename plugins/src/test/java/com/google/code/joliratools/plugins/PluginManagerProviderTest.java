/**
 * Copyright (c) 2010 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools.plugins;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.google.inject.Provider;

/**
 * @author jfk
 * @date Jul 25, 2010 7:06:46 PM
 * @since 1.0
 */
public class PluginManagerProviderTest {
    /**
     * Test method for PluginManagerProvider#PluginManagerProvider(Provider).
     */
    @Test
    public void testPluginManagerProvider() {
        final String value = "jolira";
        final Provider<String> creator = new Provider<String>() {
            @Override
            public String get() {
                return value;
            }
        };
        final Provider<String> provider = new PluginManagerProvider<String>(creator);
        final String value1 = provider.get();
        final String value2 = provider.get();

        assertSame(value, value1);
        assertSame(value, value2);
    }
}
