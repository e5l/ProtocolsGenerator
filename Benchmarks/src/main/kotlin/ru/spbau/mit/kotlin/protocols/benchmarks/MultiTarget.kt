package ru.spbau.mit.kotlin.protocols.benchmarks

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit


@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class MultiTarget {
    private val TEST_SIZE = 1000

    val testData = mutableListOf<Bar>()

    init {
        while (testData.size < TEST_SIZE) {
            testData.add(A0())
            testData.add(A1())
            testData.add(A2())
            testData.add(A3())
            testData.add(A4())
            testData.add(A5())
            testData.add(A6())
            testData.add(A7())
            testData.add(A8())
            testData.add(A9())
            testData.add(A10())
            testData.add(A11())
            testData.add(A12())
            testData.add(A13())
            testData.add(A14())
            testData.add(A15())
            testData.add(A16())
            testData.add(A17())
            testData.add(A18())
            testData.add(A19())
        }

        testData.permute()
    }

    @Benchmark
    fun test() {
        for (i: Bar in testData) {
            i.foo()
        }
    }
}
