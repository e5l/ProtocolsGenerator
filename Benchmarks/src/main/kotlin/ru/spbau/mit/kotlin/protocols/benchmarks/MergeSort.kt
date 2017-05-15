package ru.spbau.mit.kotlin.protocols.benchmarks

import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit


protocol interface ImplicitComparable {
    fun lessOrEq(other: ImplicitComparable): Boolean
}

interface ExplicitComparable {
    fun lessOrEq(other: ExplicitComparable): Boolean
}

data class ImplicitItem(val i: Int) {
    fun lessOrEq(other: ImplicitComparable) = i <= (other as ImplicitItem).i
}

class ExplicitItem(val i: Int) : ExplicitComparable {
    override fun lessOrEq(other: ExplicitComparable) = i <= (other as ExplicitItem).i
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

fun mergeExplicit(list: MutableList<ExplicitComparable>, left: Int, right: Int, end: Int) {
    val result = mutableListOf<ExplicitComparable>()
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

fun mergeSortExplicit(list: MutableList<ExplicitComparable>, l: Int, r: Int) {
    if (r - l < 1) {
        return
    }

    val middle = l + (r - l) / 2

    mergeSortExplicit(list, l, middle)
    mergeSortExplicit(list, middle + 1, r)
    mergeExplicit(list, l, middle + 1, r)
}


fun List<ImplicitComparable>.isSorted(): Boolean = (0..size-2).none { get(it).lessOrEq(get(it + 1)) }
fun List<ExplicitComparable>.isSortedExplicit(): Boolean = (0..size-2).none { get(it).lessOrEq(get(it + 1)) }

fun genListImplicit(size: Int, modulo: Int): MutableList<ImplicitComparable> {
    val result = mutableListOf<ImplicitComparable>()
    val generator = Random(2017)
    while (result.size < size) {
        result.add(ImplicitItem(generator.nextInt(modulo)))
    }

    return result
}

fun genListExplicit(size: Int, modulo: Int): MutableList<ExplicitComparable> {
    val result = mutableListOf<ExplicitComparable>()
    val generator = Random(2017)
    while (result.size < size) {
        result.add(ExplicitItem(generator.nextInt(modulo)))
    }

    return result
}

@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
open class MergeSort {
    val list10 = genListImplicit(10, 1000)
    val list100 = genListImplicit(100, 1000)
    val list1000 = genListImplicit(1000, 1000)
    val list10000 = genListImplicit(10000, 1000)
    val list100000 = genListImplicit(100000, 1000)

    val list10Explicit = genListExplicit(10, 1000)
    val list100Explicit = genListExplicit(100, 1000)
    val list1000Explicit = genListExplicit(1000, 1000)
    val list10000Explicit = genListExplicit(10000, 1000)
    val list100000Explicit = genListExplicit(100000, 1000)

    @Benchmark
    fun testImplicit10() = mergeSortImplicit(list10, 0, list10.size - 1)

    @Benchmark
    fun testImplicit100() = mergeSortImplicit(list100, 0, list100.size - 1)

    @Benchmark
    fun testImplicit1000() = mergeSortImplicit(list1000, 0, list1000.size - 1)

    @Benchmark
    fun testImplicit10000() = mergeSortImplicit(list10000, 0, list10000.size - 1)

    @Benchmark
    fun testImplicit100000() = mergeSortImplicit(list100000, 0, list100000.size - 1)

    @Benchmark
    fun testExplicit10() = mergeSortExplicit(list10Explicit, 0, list10Explicit.size - 1)

    @Benchmark
    fun testExplicit100() = mergeSortExplicit(list100Explicit, 0, list100Explicit.size - 1)

    @Benchmark
    fun testExplicit1000() = mergeSortExplicit(list1000Explicit, 0, list1000Explicit.size - 1)

    @Benchmark
    fun testExplicit10000() = mergeSortExplicit(list10000Explicit, 0, list10000Explicit.size - 1)

    @Benchmark
    fun testExplicit100000() = mergeSortExplicit(list100000Explicit, 0, list100000Explicit.size - 1)
}
