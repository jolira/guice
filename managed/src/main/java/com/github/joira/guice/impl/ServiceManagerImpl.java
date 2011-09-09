/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Provider;
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
    private static ManagedService[] sort(final Collection<ManagedService> services) {
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

    private final Provider<Set<ManagedService>> servicesProvider;

    @Inject
    ServiceManagerImpl(final Provider<Set<ManagedService>> servicesProvider, final SingletonManager singletons) {
        this.servicesProvider = servicesProvider;
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
        synchronized (servicesProvider) {
            final ManagedService[] services = getServices();

            for (final ManagedService service : services) {
                service.start();
            }
        }
    }

    private ManagedService[] getServices() {
        final Collection<ManagedService> svcs = servicesProvider.get();

        return sort(svcs);
    }

    @Override
    public void stop() {
        synchronized (servicesProvider) {
            final ManagedService[] services = getServices();

            for (int idx = services.length - 1; idx >= 0; idx--) {
                final ManagedService service = services[idx];

                service.stop();
            }
        }
    }
}
