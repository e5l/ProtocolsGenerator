package ru.spbau.mit.kotlin.protocols.benchmarks

import java.util.*

fun <T> MutableList<T>.permute() {
    if (size < 2) {
        return
    }

    val random = Random()
    for (i in 1..size - 1) {
        val j = random.nextInt(i + 1)
        val tmp = get(i)

        set(i, get(j))
        set(j, tmp)
    }
}


