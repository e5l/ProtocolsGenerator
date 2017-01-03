package ru.spbau.mit.protocols.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooClass;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooInterface;

import java.util.List;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Thread)
public class ProtocolsBenchmarkBatches {
    private List<FooClass> classInstances;
    private List<FooClass> childInstances;
    private List<FooInterface> interfaceInstances;
    private List<FooInterface> interfaceLambdaInstances;

    @Setup
    public void setup(final Blackhole blackhole) {
    }
}
