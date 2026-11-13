package cn.edu.sicnucs.bml.bimeilingcalculator.data

import android.content.Context
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONException

/**
 * 使用 SharedPreferences 保存历史记录，应用重启或屏幕旋转后记录仍然存在。
 */
object HistoryRepository {
    private const val PREF_NAME = "calculator_history"
    private const val KEY_HISTORY = "history"
    private const val MAX_RECORDS = 100

    fun load(context: Context): List<CalculationRecord> {
        val prefs = context.applicationContext
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val raw = prefs.getString(KEY_HISTORY, null) ?: return emptyList()

        return try {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    val expression = item.optString("expression")
                    val result = item.optString("result")
                    if (expression.isNotBlank() && result.isNotBlank()) {
                        add(CalculationRecord(expression, result))
                    }
                }
            }
        } catch (_: JSONException) {
            emptyList()
        }
    }

    @Synchronized
    fun add(context: Context, record: CalculationRecord) {
        val records = buildList {
            add(record)
            addAll(load(context).take(MAX_RECORDS - 1))
        }
        save(context, records)
    }

    private fun save(context: Context, records: List<CalculationRecord>) {
        val array = JSONArray()
        records.forEach { record ->
            array.put(
                org.json.JSONObject()
                    .put("expression", record.expression)
                    .put("result", record.result)
            )
        }
        context.applicationContext
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_HISTORY, array.toString())
            }
    }
}
