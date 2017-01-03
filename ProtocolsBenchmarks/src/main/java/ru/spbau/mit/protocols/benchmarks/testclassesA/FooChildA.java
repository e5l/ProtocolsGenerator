package ru.spbau.mit.protocols.benchmarks.testclassesA;

import org.openjdk.jmh.infra.Blackhole;

public class FooChildA extends FooClassA {

    public FooChildA(Blackhole blackhole) {
        super(blackhole);
    }

    @Override
    public void bar() {
        blackhole.consume(42);
    }
}
