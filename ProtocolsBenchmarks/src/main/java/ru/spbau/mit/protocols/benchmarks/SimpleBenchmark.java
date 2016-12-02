package ru.spbau.mit.protocols.benchmarks;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import ru.spbau.mit.protocols.attempt.second.*;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode({Mode.AverageTime})
public class SimpleBenchmark {

    @State(Scope.Benchmark)
    public static class ProtocolState {
        Foo instance = new Foo();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void simpleIndy(ProtocolState state) {
        Caller.foo(state.instance);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void simpleReflect(ProtocolState state) {
        Caller.reflectFoo(state.instance);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void directCall(ProtocolState state) {
        state.instance.bar();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(SimpleBenchmark.class.getSimpleName())
                .warmupIterations(20)
                .measurementIterations(20)
                .forks(1)
                .threads(1)
                .build();

        new Runner(options).run();
    }

}
