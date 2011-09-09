/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice.impl;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.github.joira.guice.ManagedService;
import com.github.joira.guice.ServiceManager;

/**
 * @author jfk
 * @date Sep 8, 2011 9:51:52 PM
 * @since 1.0
 * 
 */
@Singleton
class ServiceManagerImpl implements ServiceManager {
    private static ManagedService[] sort(final Set<ManagedService> services) {
        final TreeSet<ManagedService> _services = new TreeSet<ManagedService>(new Comparator<ManagedService>() {
            @Override
            public int compare(final ManagedService o1, final ManagedService o2) {
                final double r1 = o1.getRunLevel();
                final double r2 = o2.getRunLevel();

                if (r1 < r2) {
                    return -1;
                }

                return r1 == r2 ? 0 : 1;
            }
        });
        _services.addAll(services);

        final int size = _services.size();

        return _services.toArray(new ManagedService[size]);
    }

    private final SingletonManager singletons;

    private final ManagedService[] services;

    @Inject
    ServiceManagerImpl(final Set<ManagedService> services, final SingletonManager singletons) {
        this.services = sort(services);
        this.singletons = singletons;

        start();
    }

    @Override
    public void restart() {
        stop();
        singletons.reset();
        start();
    }

    private void start() {
        synchronized (services) {
            for (final ManagedService service : services) {
                service.start();
            }
        }
    }

    @Override
    public void stop() {
        synchronized (services) {
            for (int idx = services.length - 1; idx >= 0; idx--) {
                final ManagedService service = services[idx];

                service.stop();
            }
        }
    }

}
