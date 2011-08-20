package com.github.joira.guice.impl;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Random;

import org.junit.Test;

import com.google.inject.Key;
import com.google.inject.Provider;

@SuppressWarnings("javadoc")
public class ManagedScopeTest {

    @Test
    public void testScope() {
        final Random generator = new Random();
        final ManagedScope scope = new ManagedScope();
        final Key<Long> key = Key.get(Long.class);
        final Provider<Long> unscoped = new Provider<Long>(){
            @Override
            public Long get() {
                return Long.valueOf(generator.nextLong());
            }
        };
        final Provider<Long> p1 = scope.scope(key, unscoped);
        final Provider<Long> p2 = scope.scope(key, unscoped);
        final Long v1 = p1.get();
        final Long v2 = p2.get();

        assertSame(v1, v2);

        scope.reset();

        final Provider<Long> p3 = scope.scope(key, unscoped);
        final Long v3 = p3.get();
        final Long v4 = p1.get();

        assertNotSame(v1, v4);
        assertSame(v3, v4);
    }
}
