package cn.edu.sicnucs.bml.bimeilingcalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnucs.bml.bimeilingcalculator.data.CalculationRecord

class HistoryAdapter(
    private val records: List<CalculationRecord>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(records[position], records.size - position)
    }

    override fun getItemCount(): Int = records.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sequenceText = itemView.findViewById<TextView>(R.id.sequenceText)
        private val expressionText = itemView.findViewById<TextView>(R.id.expressionText)
        private val resultText = itemView.findViewById<TextView>(R.id.historyResultText)

        fun bind(record: CalculationRecord, sequence: Int) {
            val context = itemView.context
            sequenceText.text = context.getString(R.string.record_sequence, sequence)
            expressionText.text = record.expression
            resultText.text = context.getString(R.string.history_result_format, record.result)
            itemView.contentDescription = context.getString(
                R.string.history_item_description,
                sequence,
                record.expression,
                record.result
            )
        }
    }
}
