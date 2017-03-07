package test.fields

protocol interface Proto {
    val x: String
}

class A {
    val x: String = "OK"
}

fun test(x: Proto): String = x.x
fun box(): String = test(A())

fun main(args: Array<String>) {
    println(box())
}