package tests

import java.util.*

/**
 * Created by user on 5/15/17.
 */

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

fun mergeSort(list: MutableList<ImplicitComparable>, l: Int, r: Int, merge: (MutableList<ImplicitComparable>, Int, Int, Int) -> Unit) {
    if (r - l < 1) {
        return
    }

    val middle = l + (r - l) / 2

    mergeSort(list, l, middle, merge)
    mergeSort(list, middle + 1, r, merge)
    merge(list, l, middle + 1, r)
}

fun List<ImplicitComparable>.isSorted(): Boolean = (0..size-2).none { get(it).lessOrEq(get(it + 1)) }

fun genList(size: Int, modulo: Int): MutableList<ImplicitComparable> {
    val result = mutableListOf<ImplicitComparable>()
    val generator = Random()
    while (result.size < size) {
        result.add(ImplicitItem(generator.nextInt(modulo)))
    }

    return result
}

fun main(args: Array<String>) {
    for (size in 0..100) {
        for (modulo in 1..100) {
            for (i in 0..100) {
                val list = genList(size, modulo)
                try {
                    mergeSort(list, 0, size - 1, ::mergeImplicit)
                    assert(list.isSorted())
                } catch (t: Throwable) {
                    println("Error: $list")
                    print(t)
                    return
                }
            }
        }
    }
}
