package ru.spbau.mit.protocols.benchmarks;

import caller.Caller;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooChild;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooClass;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooImpl;
import ru.spbau.mit.protocols.benchmarks.testclasses.FooInterface;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Thread)
public class ProtocolsBenchmark {

    private FooClass classInstance;
    private FooClass childInstance;
    private FooInterface interfaceInstanceImpl;
    private FooInterface interfaceInstanceLambda;

    @Setup
    public void setup(final Blackhole blackhole) {
        classInstance = new FooClass(blackhole);
        childInstance = new FooChild(blackhole);
        interfaceInstanceLambda = () -> blackhole.consume(42);
        interfaceInstanceImpl = new FooImpl(blackhole);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void indyClass() {
        Caller.foo(classInstance);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void indyChild() {
        Caller.foo(childInstance);
    }

//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.NANOSECONDS)
//    public void indyInterfaceLambda() {
//        Caller.foo(interfaceInstanceLambda);
//    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void indyInterfaceImpl() {
        Caller.foo(interfaceInstanceImpl);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void reflectClass() {
        Caller.reflectFoo(classInstance);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void reflectChild() {
        Caller.reflectFoo(childInstance);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void reflectInterfaceLambda() {
        Caller.reflectFoo(interfaceInstanceLambda);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void reflectInterfaceImpl() {
        Caller.reflectFoo(interfaceInstanceImpl);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void directClass() {
        classInstance.bar();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void directChild() {
        childInstance.bar();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void directInterfaceLambda() {
        interfaceInstanceLambda.bar();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void directInterfaceImpl() {
        interfaceInstanceImpl.bar();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(ProtocolsBenchmark.class.getSimpleName())
                .warmupIterations(20)
                .measurementIterations(20)
                .forks(1)
                .threads(1)
                .build();

        new Runner(options).run();
    }

}
