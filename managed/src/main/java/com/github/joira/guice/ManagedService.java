/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice;

/**
 * Classes implementing this interface can be managed using the ServiceManager. This is an alternative to starting
 * services as "eager singletons". The problem with singletons is that they are started in a random sequence while the
 * services here are started according to their run-level.
 * 
 * @author jfk
 * @date Sep 8, 2011 9:34:27 PM
 * @since 1.0
 * 
 */
public interface ManagedService {
    /**
     * @return the run level. The services with the smallest run-level will be started first.
     */
    public double getRunLevel();

    /**
     * Perform the startup.
     */
    public void start();

    /**
     * Perform the shutdown.
     */
    public void stop();
}
