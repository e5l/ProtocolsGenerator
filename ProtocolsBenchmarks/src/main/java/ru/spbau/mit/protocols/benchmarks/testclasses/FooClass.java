package ru.spbau.mit.protocols.benchmarks.testclasses;

import org.openjdk.jmh.infra.Blackhole;

public class FooClass {
    protected final Blackhole blackhole;

    public FooClass(final Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    public void bar() {
        blackhole.consume(42);
    }
}
