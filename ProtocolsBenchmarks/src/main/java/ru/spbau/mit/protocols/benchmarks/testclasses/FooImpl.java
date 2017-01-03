package ru.spbau.mit.protocols.benchmarks.testclasses;

import org.openjdk.jmh.infra.Blackhole;

public class FooImpl implements FooInterface {
    private final Blackhole blackhole;

    public FooImpl(final Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    @Override
    public void bar() {
        blackhole.consume(42);
    }
}
