package ru.spbau.mit.protocols.benchmarks.testclassesB;

import org.openjdk.jmh.infra.Blackhole;

public class FooClassB {
    protected final Blackhole blackhole;

    public FooClassB(final Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    public void bar() {
        blackhole.consume(42);
    }
}
