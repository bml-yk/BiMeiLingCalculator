package cn.edu.sicnucs.bml.bimeilingcalculator

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.edu.sicnucs.bml.bimeilingcalculator.data.CalculationRecord
import cn.edu.sicnucs.bml.bimeilingcalculator.data.HistoryRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private val calculator = Calculator()

    private lateinit var leftLayout: TextInputLayout
    private lateinit var rightLayout: TextInputLayout
    private lateinit var leftInput: TextInputEditText
    private lateinit var rightInput: TextInputEditText
    private lateinit var operatorGroup: RadioGroup
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        leftLayout = findViewById(R.id.leftInputLayout)
        rightLayout = findViewById(R.id.rightInputLayout)
        leftInput = findViewById(R.id.leftInput)
        rightInput = findViewById(R.id.rightInput)
        operatorGroup = findViewById(R.id.operatorGroup)
        resultText = findViewById(R.id.resultText)

        savedInstanceState?.let { state ->
            leftInput.setText(state.getString(KEY_LEFT))
            rightInput.setText(state.getString(KEY_RIGHT))
            resultText.text = state.getString(KEY_RESULT)
                ?: getString(R.string.result_placeholder)
            val operatorId = state.getInt(KEY_OPERATOR, R.id.op_add)
            operatorGroup.check(operatorId)
        }

        findViewById<MaterialButton>(R.id.calculateButton).setOnClickListener {
            calculate()
        }
        findViewById<MaterialButton>(R.id.clearButton).setOnClickListener {
            clearForm()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_history) {
            startActivity(Intent(this, HistoryActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_LEFT, leftInput.text?.toString().orEmpty())
        outState.putString(KEY_RIGHT, rightInput.text?.toString().orEmpty())
        outState.putString(KEY_RESULT, resultText.text?.toString().orEmpty())
        outState.putInt(KEY_OPERATOR, operatorGroup.checkedRadioButtonId)
        super.onSaveInstanceState(outState)
    }

    private fun calculate() {
        leftLayout.error = null
        rightLayout.error = null

        val leftValue = leftInput.text?.toString()?.trim()?.toDoubleOrNull()
        val rightValue = rightInput.text?.toString()?.trim()?.toDoubleOrNull()
        var valid = true

        if (leftValue == null) {
            leftLayout.error = getString(
                if (leftInput.text.isNullOrBlank()) R.string.error_left_empty
                else R.string.error_number
            )
            valid = false
        }
        if (rightValue == null) {
            rightLayout.error = getString(
                if (rightInput.text.isNullOrBlank()) R.string.error_right_empty
                else R.string.error_number
            )
            valid = false
        }
        if (!valid) {
            resultText.text = getString(R.string.result_placeholder)
            return
        }

        val operator = when (operatorGroup.checkedRadioButtonId) {
            R.id.op_add -> "+"
            R.id.op_subtract -> "-"
            R.id.op_multiply -> "*"
            R.id.op_divide -> "/"
            else -> "+"
        }

        try {
            val value = calculator.calculate(leftValue!!, rightValue!!, operator)
            val formattedResult = calculator.formatResult(value)
            resultText.text = getString(R.string.result_format, formattedResult)

            HistoryRepository.add(
                this,
                CalculationRecord(
                    expression = "${leftInput.text.toString().trim()} $operator " +
                        rightInput.text.toString().trim(),
                    result = formattedResult
                )
            )
        } catch (error: ArithmeticException) {
            resultText.text = getString(R.string.calculation_error, error.message.orEmpty())
        }
    }

    private fun clearForm() {
        leftInput.text?.clear()
        rightInput.text?.clear()
        leftLayout.error = null
        rightLayout.error = null
        operatorGroup.check(R.id.op_add)
        resultText.text = getString(R.string.result_placeholder)
        leftInput.requestFocus()
    }

    private companion object {
        const val KEY_LEFT = "left"
        const val KEY_RIGHT = "right"
        const val KEY_RESULT = "result"
        const val KEY_OPERATOR = "operator"
    }
}
