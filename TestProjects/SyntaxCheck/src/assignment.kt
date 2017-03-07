package test.assignment

protocol interface Foo {
    fun foo(): String
}

class X {
    fun foo(): String = "ASDADS"
}

fun main(args: Array<String>) {
    val x: Foo = X()
    println(x.foo())
}

