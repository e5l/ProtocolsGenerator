/**
 * Created by user on 4/19/17.
 */
package hello

protocol interface X {
    fun foo(): Int
}

class B {
    fun foo(): Int = 42
}

fun run0(f: X): Int = f.foo()

fun main(args: Array<String>) {
    val x: X = B()
    println(run0(x))
}