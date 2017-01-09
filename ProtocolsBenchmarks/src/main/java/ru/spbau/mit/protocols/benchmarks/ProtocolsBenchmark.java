package ru.spbau.mit.protocols.benchmarks;

import caller.Caller;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.LinuxPerfAsmProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooChildA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooClassA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooImplA;
import ru.spbau.mit.protocols.benchmarks.testclassesA.FooInterfaceA;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Thread)
public class ProtocolsBenchmark {

    private FooClassA classInstance;
    private FooClassA childInstance;
    private FooInterfaceA interfaceInstanceImpl;
    private FooInterfaceA interfaceInstanceLambda;

    @Setup
    public void setup(final Blackhole blackhole) {
        classInstance = new FooClassA(blackhole);
        childInstance = new FooChildA(blackhole);
        interfaceInstanceLambda = () -> blackhole.consume(42);
        interfaceInstanceImpl = new FooImplA(blackhole);
    }

    @Benchmark
    public void indyClass() {
        Caller.foo(classInstance);
    }

    @Benchmark
    public void indyChild() {
        Caller.foo(childInstance);
    }

    @Benchmark
    public void indyInterfaceLambda() {
        Caller.foo(interfaceInstanceLambda);
    }

    @Benchmark
    public void indyInterfaceImpl() {
        Caller.foo(interfaceInstanceImpl);
    }

    @Benchmark
    public void reflectClass() {
        Caller.reflectFoo(classInstance);
    }

    @Benchmark
    public void reflectChild() {
        Caller.reflectFoo(childInstance);
    }

    @Benchmark
    public void reflectInterfaceLambda() {
        Caller.reflectFoo(interfaceInstanceLambda);
    }

    @Benchmark
    public void reflectInterfaceImpl() {
        Caller.reflectFoo(interfaceInstanceImpl);
    }

    @Benchmark
    public void directClass() {
        classInstance.bar();
    }

    @Benchmark
    public void directChild() {
        childInstance.bar();
    }

    @Benchmark
    public void directInterfaceLambda() {
        interfaceInstanceLambda.bar();
    }

    @Benchmark
    public void directInterfaceImpl() {
        interfaceInstanceImpl.bar();
    }
}
