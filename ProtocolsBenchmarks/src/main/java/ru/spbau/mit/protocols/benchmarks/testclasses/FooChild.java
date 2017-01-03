package ru.spbau.mit.protocols.benchmarks.testclasses;

import org.openjdk.jmh.infra.Blackhole;

public class FooChild extends FooClass {

    public FooChild(Blackhole blackhole) {
        super(blackhole);
    }

    @Override
    public void bar() {
        blackhole.consume(42);
    }
}
