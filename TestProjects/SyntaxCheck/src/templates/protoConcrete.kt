package templates.concrete

open class A
open class B: A()
open class C: B()

protocol interface Consumer {
    fun consume(a: A)
}

class Template<T> {
    fun consume(a: T) { println(a) }
}

class Concrete {
    fun consume(a: A) { println(a) }
}

fun main(arg: Array<String>) {
    try {
        val x: Consumer = Template<A>()
        x.consume(A())
    } catch (e: NoSuchMethodException) {
        println(e)
    }

    val y: Consumer = Concrete()
    y.consume(A())
}
