/**
 * Copyright (c) 2010 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools.plugins;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * @author jfk
 * @date Jul 25, 2010 7:11:01 PM
 * @since 1.0
 */
public class PluginManagerScopeTest {
    /**
     * Test method for PluginManagerScope#scope(Key, Provider)} .
     */
    @Test
    public void testScope() {
        final String value = "jolira";
        final Scope scope = new PluginManagerScope();
        final Key<String> key = Key.get(String.class);
        final Provider<String> creator = new Provider<String>() {
            @Override
            public String get() {
                return value;
            }
        };
        final Provider<String> scopedProvider1 = scope.scope(key, creator);
        final Provider<String> scopedProvider2 = scope.scope(key, creator);
        final String value1 = scopedProvider1.get();

        assertSame(scopedProvider1, scopedProvider2);
        assertSame(value, value1);
    }

}
