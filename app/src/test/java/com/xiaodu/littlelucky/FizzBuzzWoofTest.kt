package com.xiaodu.littlelucky

import org.junit.Assert
import org.junit.Test

class FizzBuzzWoofTest {

    @Test
    fun `test one`() {
        fizzBuzz(1) `should be` "1"
        fizzBuzz(2) `should be` "2"
        fizzBuzz(3) `should be` "Fizz"
        fizzBuzz(4) `should be` "4"
        fizzBuzz(5) `should be` "Buzz"
        fizzBuzz(6) `should be` "Fizz"
        fizzBuzz(7) `should be` "Woof"
        fizzBuzz(3 * 5) `should be` "FizzBuzz"
        fizzBuzz(5 * 7) `should be` "BuzzWoof"
        fizzBuzz(3 * 5 * 7) `should be` "FizzBuzzWoof"
    }

    private fun fizzBuzz(value: Int): String =
        listOf(::fizz, ::buzz, ::woof)
            .joinToString("") { it(value) }
            .ifEmpty {
                value.toString()
            }

    private fun fizz(i: Int) = i.woordOnRemainder(3, "Fizz")
    private fun buzz(i: Int) = i.woordOnRemainder(5, "Buzz")
    private fun woof(i: Int) = i.woordOnRemainder(7, "Woof")

    private fun Int.woordOnRemainder(remainder: Int, word: String): String =
        if (rem(remainder) == 0) word else ""
}

private infix fun String.`should be`(exceptd: String) {
    Assert.assertEquals(exceptd, this)
}
