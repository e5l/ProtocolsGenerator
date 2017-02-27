
protocol interface Foo {
    fun sum(a: Int, b: Int): Int

    operator fun plus(a : Int): Int
    operator fun get(a : Int): Int
    operator fun invoke(): Int
    operator fun set(a: Int, b: Int)

    val x: Int
}

class Baz {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    operator fun plus(a: Int) = a * 2
    operator fun get(a : Int) = a * 2
    operator fun invoke() = 2
    operator fun set(a: Int, b: Int) = println((a + b) * 2)

    val x: Int = 2
}

class Bar {
    val x: Int = 1
    fun sum(a: Int, b : Int): Int {
        return 2 * (a + b)
    }

    operator fun plus(a: Int) = a
    operator fun get(a : Int) = a
    operator fun invoke() = 1
    operator fun set(a: Int, b: Int) = println(a + b)
}

fun x(): Int = Math.random().toInt()

fun bar(arg: Foo) {
//    println(arg::class.java)
//    println("Function: ")
//    println(arg.sum(10000, 200000))
    println(arg.sum(x() + 200000, 200000 + x()))
//
//    println("Operators: ")
//    println(arg + 1)
//    println(arg())
//    arg[1] = 2
//    println(arg[1]) TBD
}

fun Baaam(vararg a: Any?) {
}

fun main(args: Array<String>) {
    val baz = Baz()
    val bar = Bar()
    bar(baz)
    Baaam(1, 2, 3, 4, Int.javaClass)

    val method = Baz::class.java.getMethod("sum", Int::class.java, Int::class.java)
    val i: Int = method.invoke(baz, 1, 2) as Int
    println(i)
//    bar(bar)

//    val x = (object : Foo {
//        override fun sum(a: Int, b: Int): Int = 0
//    })
}
