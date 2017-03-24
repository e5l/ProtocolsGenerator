package templates.protoXX

protocol interface Foo<T> {
    fun foo(x: T): T
}

protocol interface Baz {
    fun foo(x: Int): Int
}

class Bar<T> {
    fun foo(x: T): T = x
}

fun <T> foo(a: T, x: Foo<T>) {
    println(x.foo(a))
}

fun fooo(a: Int, x: Baz) {
    println(x.foo(a))
}

fun test() {
    foo<Int>(5, Bar<Int>())
    fooo(5, Bar<Int>())
}

fun main(args: Array<String>) {
    test()
}
