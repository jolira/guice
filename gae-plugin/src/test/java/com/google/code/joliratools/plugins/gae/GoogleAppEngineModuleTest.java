/**
 * Copyright (c) 2010 jolira. All rights reserved. This program and the accompanying materials are made available under
 * the terms of the GNU Public License 2.0 which is available at http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools.plugins.gae;

import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.PrivateBinder;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Message;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;

/**
 * @author jfk
 * @date Sep 20, 2010 5:07:32 PM
 * @since 1.0
 */
public class GoogleAppEngineModuleTest {
    /**
     * Test method for {@link GoogleAppEngineModule#configure(Binder)}.
     */
    @Test
    public void testConfigure() {
        final Module module = new GoogleAppEngineModule();

        module.configure(new Binder() {
            @Override
            public void addError(final Message message) {
                fail();
            }

            @Override
            public void addError(final String message, final Object... arguments) {
                fail();
            }

            @Override
            public void addError(final Throwable t) {
                fail();
            }

            @Override
            public <T> AnnotatedBindingBuilder<T> bind(final Class<T> type) {
                fail();
                return null;
            }

            @Override
            public <T> LinkedBindingBuilder<T> bind(final Key<T> key) {
                fail();
                return null;
            }

            @Override
            public <T> AnnotatedBindingBuilder<T> bind(final TypeLiteral<T> typeLiteral) {
                fail();
                return null;
            }

            @Override
            public AnnotatedConstantBindingBuilder bindConstant() {
                fail();
                return null;
            }

            @Override
            public void bindInterceptor(final Matcher<? super Class<?>> classMatcher,
                    final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors) {
                fail();
            }

            @Override
            public void bindListener(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeListener listener) {
                fail();
            }

            @Override
            public void bindScope(final Class<? extends Annotation> annotationType, final Scope scope) {
                fail();
            }

            @Override
            public void convertToTypes(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeConverter converter) {
                fail();
            }

            @Override
            public Stage currentStage() {
                fail();
                return null;
            }

            @Override
            public <T> MembersInjector<T> getMembersInjector(final Class<T> type) {
                fail();
                return null;
            }

            @Override
            public <T> MembersInjector<T> getMembersInjector(final TypeLiteral<T> typeLiteral) {
                fail();
                return null;
            }

            @Override
            public <T> Provider<T> getProvider(final Class<T> type) {
                fail();
                return null;
            }

            @Override
            public <T> Provider<T> getProvider(final Key<T> key) {
                fail();
                return null;
            }

            @Override
            public void install(@SuppressWarnings("hiding") final Module module) {
                fail();
            }

            @Override
            public PrivateBinder newPrivateBinder() {
                fail();
                return null;
            }

            @Override
            public void requestInjection(final Object instance) {
                fail();
            }

            @Override
            public <T> void requestInjection(final TypeLiteral<T> type, final T instance) {
                fail();
            }

            @Override
            public void requestStaticInjection(final Class<?>... types) {
                fail();
            }

            @Override
            public Binder skipSources(@SuppressWarnings("rawtypes") final Class... classesToSkip) {
                fail();
                return null;
            }

            @Override
            public Binder withSource(final Object source) {
                fail();
                return null;
            }
        });
        fail("Not yet implemented"); // TODO
    }

}
