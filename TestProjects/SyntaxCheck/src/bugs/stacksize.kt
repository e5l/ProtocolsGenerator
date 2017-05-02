package bugs

protocol interface Bar {
    fun foo()
}

class A0() {
    fun foo() { println("OK") }
}


fun main(args: Array<String>) {
    val testData = mutableListOf<Bar>()
    testData.add(A0())

    for (i: Bar in testData) {
        i.foo()
    }

}
