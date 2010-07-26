/**
 *
 */
package com.google.code.joliratools.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * @author jfk
 */
public class PluginManagerTest {
    @ManagedSingleton
    private static class MySingleton implements MySingletonInterface {
        // do nothing
    }

    private static interface MySingletonInterface {
        // do nothing
    }

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

    /**
    *
    */
    @Test
    public void testScoping() {
        final Module module = new Module() {
            @Override
            public void configure(final Binder binder) {
                binder.bind(MySingletonInterface.class).to(MySingleton.class);
            }
        };
        final PluginManager mgr1 = new PluginManager(module);
        final Injector injector1 = mgr1.getInjector();
        final MySingletonInterface i1 = injector1.getInstance(MySingletonInterface.class);
        final MySingletonInterface i2 = injector1.getInstance(MySingletonInterface.class);

        assertSame(i1, i2);

        final PluginManager mgr2 = new PluginManager(module);
        final Injector injector2 = mgr2.getInjector();
        final MySingletonInterface i3 = injector2.getInstance(MySingletonInterface.class);
        final MySingletonInterface i4 = injector2.getInstance(MySingletonInterface.class);

        assertSame(i3, i4);
        assertNotSame(i1, i3);
    }
}
