package ru.spbau.mit.protocols.benchmarks.testclassesB;

import org.openjdk.jmh.infra.Blackhole;

public class FooChildB extends FooClassB {

    public FooChildB(Blackhole blackhole) {
        super(blackhole);
    }

    @Override
    public void bar() {
        blackhole.consume(42);
    }
}
