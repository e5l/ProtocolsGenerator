/**
 * Created by user on 3/6/17.
 */
package test.rt

protocol interface Proto {
    fun foo(): String
}

class Impl {
    fun foo(): String = "OK"
}

fun id(x: Proto): Proto {
    println("OK")
    return x
}

fun box(): String = id(Impl()).foo()

fun main(str: Array<String>) {
    println(box())
}
