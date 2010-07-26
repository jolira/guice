/**
 * Copyright (c) 2010 jolira.
 * All rights reserved. This program and the accompanying 
 * materials are made available under the terms of the 
 * GNU Public License 2.0 which is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools.plugins;

import com.google.inject.Provider;

class PluginManagerProvider<T> implements Provider<T> {
    T cached = null;
    private final Provider<T> creator;
    private boolean resolved = false;

    PluginManagerProvider(final Provider<T> creator) {
        this.creator = creator;
    }

    @Override
    public synchronized T get() {
        if (!resolved) {
            resolved = true;
            cached = creator.get();
        }

        return cached;
    }
}