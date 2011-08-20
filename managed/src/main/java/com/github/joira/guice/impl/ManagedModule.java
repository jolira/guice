/**
 * Copyright (c) 2011 jolira.
 * All rights reserved. This program and the accompanying
 * materials are made available under the terms of the
 * GNU Public License 2.0 which is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice.impl;

import com.github.joira.guice.SingletonManager;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author jfk
 * @date Aug 19, 2011 9:40:51 PM
 * @since 1.0
 *
 */
public class ManagedModule implements Module {
    @SuppressWarnings("deprecation")
    @Override
    public void configure(final Binder binder) {
        final ManagedScope scope = new ManagedScope();

        binder.bindScope(com.github.joira.guice.ManagedSingleton.class, scope);
        binder.bindScope(com.google.code.joliratools.plugins.ManagedSingleton.class, scope);
        binder.bind(SingletonManager.class).toInstance(scope);
    }

}
