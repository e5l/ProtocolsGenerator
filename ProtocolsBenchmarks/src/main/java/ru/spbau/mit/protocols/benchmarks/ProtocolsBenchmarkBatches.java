package ru.spbau.mit.protocols.benchmarks;

import caller.Caller;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooChildA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooClassA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooImplA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooInterfaceA;
import ru.spbau.mit.protocols.benchmarks.testclassesB.FooChildB;
import ru.spbau.mit.protocols.benchmarks.testclassesB.FooClassB;
import ru.spbau.mit.protocols.benchmarks.testclassesB.FooImplB;
import ru.spbau.mit.protocols.benchmarks.testclassesB.FooInterfaceB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Thread)
public class ProtocolsBenchmarkBatches {
    private static final int BATCH_SIZE = 1000;

    private final List<Object> classInstances = new ArrayList<>();
    private final List<Object> childInstances = new ArrayList<>();
    private final List<Object> interfaceInstances = new ArrayList<>();
    private final List<Object> interfaceLambdaInstances = new ArrayList<>();

    @Setup
    public void setup(final Blackhole blackhole) {
        for (int i = 0; i < BATCH_SIZE / 2; i++) {
            classInstances.add(new FooClassA(blackhole));
            childInstances.add(new FooChildA(blackhole));
            interfaceInstances.add(new FooImplA(blackhole));
            interfaceLambdaInstances.add((FooInterfaceA) () -> blackhole.consume(42));
        }

        for (int i = BATCH_SIZE / 2; i < BATCH_SIZE; i++) {
            classInstances.add(new FooClassB(blackhole));
            childInstances.add(new FooChildB(blackhole));
            interfaceInstances.add(new FooImplB(blackhole));
            interfaceLambdaInstances.add((FooInterfaceB) () -> blackhole.consume(42));
        }

        Collections.shuffle(classInstances);
        Collections.shuffle(childInstances);
        Collections.shuffle(interfaceInstances);
        Collections.shuffle(interfaceLambdaInstances);
    }

    @Benchmark
    public void indyClass() {
        for (Object instance : classInstances) {
            Caller.foo(instance);
        }
    }

    @Benchmark
    public void indyChild() {
        for (Object instance : classInstances) {
            Caller.foo(instance);
        }
    }

    @Benchmark
    public void indyInterfaceLambda() {
        for (Object instance : interfaceLambdaInstances) {
            Caller.foo(instance);
        }
    }

    @Benchmark
    public void indyInterfaceImpl() {
        for (Object instance : interfaceInstances) {
            Caller.foo(instance);
        }
    }

    @Benchmark
    public void reflectClass() {
        for (Object instance : classInstances) {
            Caller.reflectFoo(instance);
        }
    }

    @Benchmark
    public void reflectChild() {
        for (Object instance : childInstances) {
            Caller.reflectFoo(instance);
        }
    }

    @Benchmark
    public void reflectInterfaceLambda() {
        for (Object instance : interfaceLambdaInstances) {
            Caller.reflectFoo(instance);
        }
    }

    @Benchmark
    public void reflectInterfaceImpl() {
        for (Object instance : interfaceInstances) {
            Caller.reflectFoo(instance);
        }
    }

}
