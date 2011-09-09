/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

/**
 * @author jfk
 * @date Aug 19, 2011 9:54:33 PM
 * @since 1.0
 * 
 */
class ManagedScope implements Scope, SingletonManager {
    private final Map<Key<?>, Object> scopedByKey = new HashMap<Key<?>, Object>();

    @Override
    public void reset() {
        synchronized (scopedByKey) {
            scopedByKey.clear();
        }
    }

    <T> T getScoped(final Key<T> key, final Provider<T> unscoped) {
        synchronized (scopedByKey) {
            @SuppressWarnings("unchecked")
            final T cached = (T) scopedByKey.get(key);

            if (cached != null) {
                return cached;
            }

            final T i = unscoped.get();

            scopedByKey.put(key, i);

            return i;
        }
    }

    @Override
    public <T> Provider<T> scope(final Key<T> key, final Provider<T> unscoped) {
        return new Provider<T>() {
            @Override
            public T get() {
                return getScoped(key, unscoped);
            }
        };
    }
}
