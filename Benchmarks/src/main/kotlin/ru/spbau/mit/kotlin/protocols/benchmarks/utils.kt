package ru.spbau.mit.kotlin.protocols.benchmarks

import java.util.*

fun <T> MutableList<T>.permute() {
    if (size < 2) {
        return
    }

    var i = 1
    val random = Random()

    while (i < size) {
        val j = random.nextInt(i + 1)
        val tmp = get(i)

        set(i, get(j))
        set(j, tmp)
        ++i
    }
}


