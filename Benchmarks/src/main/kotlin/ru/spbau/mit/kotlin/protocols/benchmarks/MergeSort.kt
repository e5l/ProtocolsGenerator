package ru.spbau.mit.kotlin.protocols.benchmarks

import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit


protocol interface ImplicitComparable {
    fun lessOrEq(other: ImplicitComparable): Boolean
}

data class ImplicitItem(val i: Int) {
    fun lessOrEq(other: ImplicitComparable) = i <= (other as ImplicitItem).i
}

fun mergeImplicit(list: MutableList<ImplicitComparable>, left: Int, right: Int, end: Int) {
    val result = mutableListOf<ImplicitComparable>()
    if (left > right || right > end) {
        return
    }

    var l = left
    var r = right
    val size = end - left + 1

    while(result.size < size) {
        if (r > end || (l <= left && list[l].lessOrEq(list[r]))) {
            result.add(list[l])
            l++
            continue
        }

        result.add(list[r])
        r++
    }

    for (i in left..end) {
        list[i] = result[i - left]
    }

}

fun mergeSortImplicit(list: MutableList<ImplicitComparable>, l: Int, r: Int) {
    if (r - l < 1) {
        return
    }

    val middle = l + (r - l) / 2

    mergeSortImplicit(list, l, middle)
    mergeSortImplicit(list, middle + 1, r)
    mergeImplicit(list, l, middle + 1, r)
}



fun List<ImplicitComparable>.isSorted(): Boolean = (0..size-2).none { get(it).lessOrEq(get(it + 1)) }

fun genListImplicit(size: Int): MutableList<ImplicitComparable> {
    val result = mutableListOf<ImplicitComparable>()
    val generator = Random()
    while (result.size < size) {
        result.add(ImplicitItem(generator.nextInt()))
    }

    return result
}

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class MergeSort {

    @State(Scope.Benchmark)
    open class X {
        val list10 = genListImplicit(10)
        val list50 = genListImplicit(50)
        val list100 = genListImplicit(100)
        val list500 = genListImplicit(500)
        val list1000 = genListImplicit(1000)
        val list5000 = genListImplicit(5000)
        val list10000 = genListImplicit(10000)
    }


    @Benchmark
    fun testImplicit10(state: X) = mergeSortImplicit(state.list10, 0, state.list10.size - 1)

    @Benchmark
    fun testImplicit50(state: X) = mergeSortImplicit(state.list50, 0, state.list50.size - 1)

    @Benchmark
    fun testImplicit100(state: X) = mergeSortImplicit(state.list100, 0, state.list100.size - 1)

    @Benchmark
    fun testImplicit500(state: X) = mergeSortImplicit(state.list500, 0, state.list500.size - 1)

    @Benchmark
    fun testImplicit1000(state: X) = mergeSortImplicit(state.list1000, 0, state.list1000.size - 1)

    @Benchmark
    fun testImplicit5000(state: X) = mergeSortImplicit(state.list5000, 0, state.list5000.size - 1)

    @Benchmark
    fun testImplicit10000(state: X) = mergeSortImplicit(state.list10000, 0, state.list10000.size - 1)
}
