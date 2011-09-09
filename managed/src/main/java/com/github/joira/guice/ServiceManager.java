/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.github.joira.guice;

/**
 * Manages the services and managed singletons. Allows the singletons to be reset and services to be restarted.
 * 
 * @author jfk
 * @date Sep 8, 2011 9:44:40 PM
 * @since 1.0
 * 
 */
public interface ServiceManager {
    /**
     * Stop the registered services. The service with the highest level will be stoppped first.
     */
    public void stop();

    /**
     * First stop and than start. Also resets all managed singletons.
     * 
     */
    public void restart();
}
