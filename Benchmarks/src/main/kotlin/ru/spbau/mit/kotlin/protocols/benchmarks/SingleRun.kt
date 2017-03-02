package ru.spbau.mit.kotlin.protocols.benchmarks

import org.openjdk.jmh.annotations.*
import ru.spbau.mit.kotlin.protocols.benchmarks.Foo
import ru.spbau.mit.kotlin.protocols.benchmarks.FooImpl1
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.concurrent.TimeUnit

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class SingleRun {

    val x: FooImpl1 = FooImpl1()

    @Benchmark
    fun args0(): Int = run0(x)

    @Benchmark
    fun args1(): Int = run1(x)

    @Benchmark
    fun args2(): Int = run2(x)

    @Benchmark
    fun args3(): Int = run3(x)

    @Benchmark
    fun args4(): Int = run4(x)

    @Benchmark
    fun args5(): Int = run5(x)

    @Benchmark
    fun args10(): Int = run10(x)

    @Benchmark
    fun args15(): Int = run15(x)

    @Benchmark
    fun args20(): Int = run20(x)
}
