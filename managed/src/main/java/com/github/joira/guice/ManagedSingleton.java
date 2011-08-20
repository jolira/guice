/**
 * Copyright (c) 2010 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package com.github.joira.guice;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.ScopeAnnotation;
import com.google.inject.Singleton;

/**
 * Apply this to implementation classes when you want only one instance to be reused for all injections for that
 * binding, but if you want to be able to reset the instance during the lifetime of the app.
 * 
 * @author Joachim F. Kainz
 * @date Jul 25, 2010 5:16:26 PM
 * @since 2.0.4
 * @see Singleton
 */
@Target({ TYPE })
@Retention(RUNTIME)
@ScopeAnnotation
@Documented
public @interface ManagedSingleton {
    // nothing
}
