/**
 * Copyright (c) 2010 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools.plugins;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * @author jfk
 * @date Jul 25, 2010 5:09:55 PM
 * @since 1.0
 */
final class PluginManagerScope implements Scope {
    private final Map<Key<?>, Provider<?>> cachedValues = new HashMap<Key<?>, Provider<?>>();

    /**
     * @see Scope#scope(Key, Provider)
     */
    @Override
    public synchronized <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
        @SuppressWarnings("unchecked")
        Provider<T> provider = (Provider<T>) cachedValues.get(key);

        if (provider == null) {
            provider = new PluginManagerProvider<T>(creator);

            cachedValues.put(key, provider);
        }

        return provider;
    }
}
