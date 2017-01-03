package ru.spbau.mit.protocols.benchmarks.testclassesB;

import org.openjdk.jmh.infra.Blackhole;

public class FooImplB implements FooInterfaceB {
    private final Blackhole blackhole;

    public FooImplB(final Blackhole blackhole) {
        this.blackhole = blackhole;
    }

    @Override
    public void bar() {
        blackhole.consume(42);
    }
}
