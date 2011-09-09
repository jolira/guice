package com.github.joira.guice.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Message;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;

@SuppressWarnings("javadoc")
public class ManagedModuleTest {

    @Test
    public void testConfigure() {
        final Module module = new ManagedModule();

        module.configure(new Binder() {
            @Override
            public void bindInterceptor(final Matcher<? super Class<?>> classMatcher, final Matcher<? super Method> methodMatcher,
                    final MethodInterceptor... interceptors) {
                fail();
            }

            @Override
            public void bindScope(final Class<? extends Annotation> annotationType, final Scope scope) {
                assertNotNull(scope);
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
            public <T> AnnotatedBindingBuilder<T> bind(final Class<T> type) {
                return new AnnotatedBindingBuilder<T>(){
                    @Override
                    public ScopedBindingBuilder to(final Class<? extends T> implementation) {
                        return new ScopedBindingBuilder(){

                            @Override
                            public void in(final Class<? extends Annotation> scopeAnnotation) {
                                fail();
                            }

                            @Override
                            public void in(final Scope scope) {
                                fail();
                            }

                            @Override
                            public void asEagerSingleton() {
                                // nothing
                            }};
                    }

                    @Override
                    public ScopedBindingBuilder to(final TypeLiteral<? extends T> implementation) {
                        fail();
                        return null;
                    }

                    @Override
                    public ScopedBindingBuilder to(final Key<? extends T> targetKey) {
                        fail();
                        return null;
                    }

                    @Override
                    public void toInstance(final T instance) {
                        assertNotNull(instance);
                    }

                    @Override
                    public ScopedBindingBuilder toProvider(final Provider<? extends T> provider) {
                        fail();
                        return null;
                    }

                    @Override
                    public ScopedBindingBuilder toProvider(
                            final Class<? extends javax.inject.Provider<? extends T>> providerType) {
                        fail();
                        return null;
                    }

                    @Override
                    public ScopedBindingBuilder toProvider(
                            final TypeLiteral<? extends javax.inject.Provider<? extends T>> providerType) {
                        fail();
                        return null;
                    }

                    @Override
                    public ScopedBindingBuilder toProvider(final Key<? extends javax.inject.Provider<? extends T>> providerKey) {
                        fail();
                        return null;
                    }

                    @Override
                    public <S extends T> ScopedBindingBuilder toConstructor(final Constructor<S> constructor) {
                        fail();
                        return null;
                    }

                    @Override
                    public <S extends T> ScopedBindingBuilder toConstructor(final Constructor<S> constructor,
                            final TypeLiteral<? extends S> _type) {
                        fail();
                        return null;
                    }

                    @Override
                    public void in(final Class<? extends Annotation> scopeAnnotation) {
                        fail();

                    }

                    @Override
                    public void in(final Scope scope) {
                        fail();

                    }

                    @Override
                    public void asEagerSingleton() {
                        fail();

                    }

                    @Override
                    public LinkedBindingBuilder<T> annotatedWith(final Class<? extends Annotation> annotationType) {
                        fail();
                        return null;
                    }

                    @Override
                    public LinkedBindingBuilder<T> annotatedWith(final Annotation annotation) {
                        fail();
                        return null;
                    }
                };
            }

            @Override
            public AnnotatedConstantBindingBuilder bindConstant() {
                fail();
                return null;
            }

            @Override
            public <T> void requestInjection(final TypeLiteral<T> type, final T instance) {
                fail();
            }

            @Override
            public void requestInjection(final Object instance) {
                fail();
            }

            @Override
            public void requestStaticInjection(final Class<?>... types) {
                fail();
            }

            @Override
            public void install(final Module mdl) {
                fail();
            }

            @Override
            public Stage currentStage() {
                fail();
                return null;
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
            public void addError(final Message message) {
                fail();

            }

            @Override
            public <T> Provider<T> getProvider(final Key<T> key) {
                fail();
                return null;
            }

            @Override
            public <T> Provider<T> getProvider(final Class<T> type) {
                fail();
                return null;
            }

            @Override
            public <T> MembersInjector<T> getMembersInjector(final TypeLiteral<T> typeLiteral) {
                fail();
                return null;
            }

            @Override
            public <T> MembersInjector<T> getMembersInjector(final Class<T> type) {
                fail();
                return null;
            }

            @Override
            public void convertToTypes(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeConverter converter) {
                fail();
            }

            @Override
            public void bindListener(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeListener listener) {
                fail();
            }

            @Override
            public Binder withSource(final Object source) {
                fail();
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            public Binder skipSources(final Class... classesToSkip) {
                return new Binder(){

                    @Override
                    public void bindInterceptor(final Matcher<? super Class<?>> classMatcher,
                            final Matcher<? super Method> methodMatcher, final MethodInterceptor... interceptors) {
                        fail();
                    }

                    @Override
                    public void bindScope(final Class<? extends Annotation> annotationType, final Scope scope) {
                        fail();
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
                    public <T> AnnotatedBindingBuilder<T> bind(final Class<T> type) {
                        fail();
                        return null;
                    }

                    @Override
                    public AnnotatedConstantBindingBuilder bindConstant() {
                        fail();
                        return null;
                    }

                    @Override
                    public <T> void requestInjection(final TypeLiteral<T> type, final T instance) {
                        fail();
                    }

                    @Override
                    public void requestInjection(final Object instance) {
                        fail();
                    }

                    @Override
                    public void requestStaticInjection(final Class<?>... types) {
                        fail();
                    }

                    @Override
                    public void install(final Module module1) {
                        // nothing;
                    }

                    @Override
                    public Stage currentStage() {
                        fail();
                        return null;
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
                    public void addError(final Message message) {
                        fail();
                    }

                    @Override
                    public <T> Provider<T> getProvider(final Key<T> key) {
                        fail();
                        return null;
                    }

                    @Override
                    public <T> Provider<T> getProvider(final Class<T> type) {
                        fail();
                        return null;
                    }

                    @Override
                    public <T> MembersInjector<T> getMembersInjector(final TypeLiteral<T> typeLiteral) {
                        fail();
                        return null;
                    }

                    @Override
                    public <T> MembersInjector<T> getMembersInjector(final Class<T> type) {
                        fail();
                        return null;
                    }

                    @Override
                    public void convertToTypes(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeConverter converter) {
                        fail();
                    }

                    @Override
                    public void bindListener(final Matcher<? super TypeLiteral<?>> typeMatcher, final TypeListener listener) {
                        fail();
                    }

                    @Override
                    public Binder withSource(final Object source) {
                        fail();
                        return null;
                    }

                    @Override
                    public Binder skipSources(final Class... classesToSkip1) {
                        fail();
                        return null;
                    }

                    @Override
                    public PrivateBinder newPrivateBinder() {
                        fail();
                        return null;
                    }

                    @Override
                    public void requireExplicitBindings() {
                        fail();
                    }

                    @Override
                    public void disableCircularProxies() {
                        fail();
                    }};
            }

            @Override
            public PrivateBinder newPrivateBinder() {
                fail();
                return null;
            }

            @Override
            public void requireExplicitBindings() {
                fail();
            }

            @Override
            public void disableCircularProxies() {
                fail();
            }});
    }

}
