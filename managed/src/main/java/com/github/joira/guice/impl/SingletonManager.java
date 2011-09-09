/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package com.github.joira.guice.impl;

import com.github.joira.guice.ManagedSingleton;

/**
 * The interface for managing instances annotated with {@link ManagedSingleton}.
 * 
 * @author jfk
 * @date Aug 19, 2011 9:48:14 PM
 * @since 1.0
 * 
 */
interface SingletonManager {
    /**
     * Reset all managed singleton.
     * 
     */
    public void reset();
}
