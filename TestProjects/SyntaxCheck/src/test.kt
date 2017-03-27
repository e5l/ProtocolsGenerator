/**
 * Created by user on 3/24/17.
 */

fun withArray() {
    val x = X()
    x.x()
    x.x(5)
}

class X {
    fun x() {}
    fun x(i: Int) {}
}