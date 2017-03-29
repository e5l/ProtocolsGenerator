package ru.spbau.mit.kotlin.protocols.benchmarks

import kotlin.*

protocol interface Foo {
    fun bar0(): Int
    fun bar1(arg1: Int): Int
    fun bar2(arg1: Int, arg2: Int): Int
    fun bar3(arg1: Int, arg2: Int, arg3: Int): Int
    fun bar4(arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int
    fun bar5(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int
    fun bar10(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int, arg7: Int, arg8: Int, arg9: Int, arg10: Int): Int
    fun bar15(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int, arg7: Int, arg8: Int, arg9: Int, arg10: Int, arg11: Int, arg12: Int, arg13: Int, arg14: Int, arg15: Int): Int
    fun bar20(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int, arg7: Int, arg8: Int, arg9: Int, arg10: Int, arg11: Int, arg12: Int, arg13: Int, arg14: Int, arg15: Int, arg16: Int, arg17: Int, arg18: Int, arg19: Int, arg20: Int): Int
}

class FooImpl1 {
    fun bar0(): Int = 0
    fun bar1(arg1: Int): Int = arg1
    fun bar2(arg1: Int, arg2: Int): Int = arg1 + arg2
    fun bar3(arg1: Int, arg2: Int, arg3: Int): Int = arg1 + arg2 + arg3
    fun bar4(arg1: Int, arg2: Int, arg3: Int, arg4: Int): Int = arg1 + arg2 + arg3 + arg4
    fun bar5(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int): Int = arg1 + arg2 + arg3 + arg4 + arg5

    fun bar10(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int, arg7: Int, arg8: Int, arg9: Int, arg10: Int): Int
            = arg1 + arg2 + arg3 + arg4 + arg5 + arg6 + arg7 + arg8 + arg9 + arg10

    fun bar15(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int, arg7: Int, arg8: Int, arg9: Int, arg10: Int, arg11: Int, arg12: Int, arg13: Int, arg14: Int, arg15: Int): Int
            = arg1 + arg2 + arg3 + arg4 + arg5 + arg6 + arg7 + arg8 + arg9 + arg10 + arg11 + arg12 + arg13 + arg14 + arg15

    fun bar20(arg1: Int, arg2: Int, arg3: Int, arg4: Int, arg5: Int, arg6: Int, arg7: Int, arg8: Int, arg9: Int, arg10: Int, arg11: Int, arg12: Int, arg13: Int, arg14: Int, arg15: Int, arg16: Int, arg17: Int, arg18: Int, arg19: Int, arg20: Int): Int
            = arg1 + arg2 + arg3 + arg4 + arg5 + arg6 + arg7 + arg8 + arg9 + arg10 + arg11 + arg12 + arg13 + arg14 + arg15 + arg16 + arg17 + arg18 + arg19 + arg20
}

fun run0(x: Foo): Int = x.bar0()
fun run1(x: Foo): Int = x.bar1(1)
fun run2(x: Foo): Int = x.bar2(1, 2)
fun run3(x: Foo): Int = x.bar3(1, 2, 3)
fun run4(x: Foo): Int = x.bar4(1, 2, 3, 4)
fun run5(x: Foo): Int = x.bar5(1, 2, 3, 4, 5)
fun run10(x: Foo): Int = x.bar10(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
fun run15(x: Foo): Int = x.bar15(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
fun run20(x: Foo): Int = x.bar20(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
