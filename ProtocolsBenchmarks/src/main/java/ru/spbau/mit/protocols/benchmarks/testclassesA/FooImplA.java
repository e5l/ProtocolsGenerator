package ru.spbau.mit.protocols.benchmarks.testclassesA;

import org.openjdk.jmh.infra.Blackhole;

public class FooImplA implements FooInterfaceA {
    private final Blackhole blackhole;

    public FooImplA(final Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    @Override
    public void bar() {
        blackhole.consume(42);
    }
}
