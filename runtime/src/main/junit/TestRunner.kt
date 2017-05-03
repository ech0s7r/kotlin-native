package org.junit
class AssertionException:RuntimeException()
class TestSuite(val beforeClass:()->Unit, val afterClass:() -> Unit, val before:() -> Unit, val after:() -> Unit, val funcs:Array<() -> Unit>) {
    internal fun run() {
        before()
        funcs.forEach{
            try {
                it()
            }
            catch (e: AssertionException) {
                println("$it failed")
            }
            catch (e: Throwable) {
                println("$it error")
            }
        }
        after()
    }
}

class TestRunner() {
    companion object {
        internal val suites = mutableListOf<TestSuite>()

        fun register(suite: TestSuite) = suites.add(suite)

        fun register(suites: List<TestSuite>) = this.suites.addAll(suites)

        internal fun run() {
            suites.forEach { it.beforeClass() }
            suites.forEach { it.run() }
            suites.forEach { it.afterClass() }
        }
    }
}

fun main() {
    TestRunner.run()
}
