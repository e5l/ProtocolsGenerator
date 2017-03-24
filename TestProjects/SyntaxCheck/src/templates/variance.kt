package templates

protocol interface Plain {
    fun consume(a: Any)
}

protocol interface Inv<T> {
    fun consume(a: T)
}

protocol interface Consumer<in T> {
    fun consume(a: T)
}

protocol interface Emitter<out T> {
    fun emit(): T
}

open class A
open class B : A()
class C : B()

fun main(args: Array<String>) {
    val plain: Plain = object {
        fun consume(a: Any) { println(a) }
    }

    plain.consume(5)

    val inv: Inv<A> = object {
        fun consume(a: A) { println(a) }
    }

    try {
        inv.consume(A())
    } catch (e: NoSuchMethodException) {
        println(e.message)
    }

    val a: Consumer<A> = object {
        fun consume(a: A) { println(a) }
    }

    a.consume(A())
}
