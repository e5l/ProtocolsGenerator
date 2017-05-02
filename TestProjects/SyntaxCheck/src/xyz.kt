/**
 * Created by user on 4/19/17.
 */
package hello

protocol interface X {
    fun foo(): String
}

open class B {
    fun foo() = "OK"
}

class A : X by B()

fun main(args: Array<String>) {
    val x: X = A()
    println(x.foo())
}