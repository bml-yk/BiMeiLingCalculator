package cn.edu.sicnucs.bml.bimeilingcalculator

import java.math.BigDecimal

/**
 * 计算器业务类：只负责两个数的四则运算与结果格式化。
 * 与 Activity 分离，便于单元测试和复用。
 */
class Calculator {
    fun calculate(left: Double, right: Double, operator: String): Double {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "*" -> left * right
            "/" -> {
                if (right == 0.0) {
                    throw ArithmeticException("除数不能为 0")
                }
                left / right
            }
            else -> throw IllegalArgumentException("不支持的运算符：$operator")
        }
    }

    fun formatResult(value: Double): String {
        if (!value.isFinite()) {
            throw ArithmeticException("计算结果超出范围")
        }
        val decimal = if (value == 0.0) {
            BigDecimal.ZERO
        } else {
            BigDecimal.valueOf(value)
        }
        return decimal.stripTrailingZeros().toPlainString()
    }
}
