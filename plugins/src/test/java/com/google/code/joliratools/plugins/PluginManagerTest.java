/**
 *
 */
package com.google.code.joliratools.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * @author jfk
 */
public class PluginManagerTest {
    /**
     */
    public static class MyTest {
        /**
         * @param abcde
         */
        @Inject
        public MyTest(@Named("abc") final Set<String> abcde) {
            assertEquals(setOf("A", "B", "C"), abcde);
        }
    }

    static <T> Set<T> setOf(final T... elements) {
        final Set<T> result = new HashSet<T>();
        result.addAll(Arrays.asList(elements));
        return result;
    }

    final TypeLiteral<Set<String>> setOfString = new TypeLiteral<Set<String>>() {
        // nothing
    };

    /**
     *
     */
    @Test
    public void testMain() {
        final PluginManager mgr = new PluginManager();
        final Injector injector = mgr.getInjector();

        assertNotNull(injector);
    }

    /**
     *
     */
    @Test
    public void testMultibinderAggregationForAnnotationInstance() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                Multibinder<String> multibinder = Multibinder.newSetBinder(binder(), String.class, Names.named("abc"));
                multibinder.addBinding().toInstance("A");
                multibinder.addBinding().toInstance("B");

                multibinder = Multibinder.newSetBinder(binder(), String.class, Names.named("abc"));
                multibinder.addBinding().toInstance("C");
            }
        });

        final Set<String> abcde = injector.getInstance(Key.get(setOfString, Names.named("abc")));
        assertEquals(setOf("A", "B", "C"), abcde);
    }

    /**
     *
     */
    @Test
    public void testMultibinderInjectSet() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                Multibinder<String> multibinder = Multibinder.newSetBinder(binder(), String.class, Names.named("abc"));
                multibinder.addBinding().toInstance("A");
                multibinder.addBinding().toInstance("B");

                multibinder = Multibinder.newSetBinder(binder(), String.class, Names.named("abc"));
                multibinder.addBinding().toInstance("C");
            }
        });

        final MyTest myTest = injector.getInstance(MyTest.class);

        assertNotNull(myTest);
    }
}
