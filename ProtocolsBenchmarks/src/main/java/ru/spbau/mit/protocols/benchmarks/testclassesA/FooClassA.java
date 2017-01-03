package ru.spbau.mit.protocols.benchmarks.testclassesA;

import org.openjdk.jmh.infra.Blackhole;

public class FooClassA {
    protected final Blackhole blackhole;

    public FooClassA(final Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    public void bar() {
        blackhole.consume(42);
    }
}
