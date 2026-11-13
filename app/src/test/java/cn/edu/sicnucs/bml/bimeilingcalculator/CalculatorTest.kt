package cn.edu.sicnucs.bml.bimeilingcalculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CalculatorTest {
    private val calculator = Calculator()

    @Test
    fun calculate_supportsFourOperations() {
        assertEquals(9.0, calculator.calculate(4.0, 5.0, "+"), 0.000001)
        assertEquals(-1.0, calculator.calculate(4.0, 5.0, "-"), 0.000001)
        assertEquals(20.0, calculator.calculate(4.0, 5.0, "*"), 0.000001)
        assertEquals(0.8, calculator.calculate(4.0, 5.0, "/"), 0.000001)
    }

    @Test
    fun calculate_rejectsDivisionByZero() {
        assertThrows(ArithmeticException::class.java) {
            calculator.calculate(4.0, 0.0, "/")
        }
    }

    @Test
    fun formatResult_removesUnnecessaryZeroes() {
        assertEquals("2.5", calculator.formatResult(2.5000))
        assertEquals("3", calculator.formatResult(3.0000))
        assertEquals("0", calculator.formatResult(-0.0))
    }
}
