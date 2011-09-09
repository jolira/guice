/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice.impl;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Provider;

import org.junit.Test;

import com.github.joira.guice.ManagedService;

@SuppressWarnings("javadoc")
public class ServiceManagerImplTest {

    @Test
    public void testServiceManagerImpl() {
        final double[] runLevel = new double[] { 0.0 };
        final int[] resetCallCount = new int[] { 0 };
        final Set<ManagedService> services = new HashSet<ManagedService>();

        services.add(new ManagedService() {
            @Override
            public double getRunLevel() {
                return 3.0;
            }

            @Override
            public void start() {
                final double level = getRunLevel();

                if (runLevel[0] >= level) {
                    fail();
                }

                runLevel[0] = level;
            }

            @Override
            public void stop() {
                final double level = getRunLevel();

                if (runLevel[0] < level) {
                    fail();
                }

                runLevel[0] = level;
            }

            @Override
            public String toString() {
                final double level = getRunLevel();

                return Double.toString(level);
            }
        });
        services.add(new ManagedService() {
            @Override
            public double getRunLevel() {
                return 1.0;
            }

            @Override
            public void start() {
                final double level = getRunLevel();

                if (runLevel[0] > level) {
                    fail();
                }

                runLevel[0] = level;
            }

            @Override
            public void stop() {
                final double level = getRunLevel();

                if (runLevel[0] < level) {
                    fail();
                }

                runLevel[0] = level;
            }

            @Override
            public String toString() {
                final double level = getRunLevel();

                return Double.toString(level);
            }
        });
        services.add(new ManagedService() {
            @Override
            public double getRunLevel() {
                return 2.0;
            }

            @Override
            public void start() {
                final double level = getRunLevel();

                if (runLevel[0] >= level) {
                    fail();
                }

                runLevel[0] = level;
            }

            @Override
            public void stop() {
                final double level = getRunLevel();

                if (runLevel[0] < level) {
                    fail();
                }

                runLevel[0] = level;
            }

            @Override
            public String toString() {
                final double level = getRunLevel();

                return Double.toString(level);
            }
        });

        final Provider<Set<ManagedService>> provider = new Provider<Set<ManagedService>>(){
            @Override
            public Set<ManagedService> get() {
                return services;
            }
        };
        final SingletonManager singletons = new SingletonManager() {
            @Override
            public void reset() {
                resetCallCount[0]++;
            }
        };
        final ServiceManagerImpl manager = new ServiceManagerImpl(provider, singletons);

        manager.restart();
        manager.stop();
    }

}
