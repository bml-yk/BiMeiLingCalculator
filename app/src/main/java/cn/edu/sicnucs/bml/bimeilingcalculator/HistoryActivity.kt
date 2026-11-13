package cn.edu.sicnucs.bml.bimeilingcalculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.edu.sicnucs.bml.bimeilingcalculator.data.HistoryRepository
import com.google.android.material.appbar.MaterialToolbar

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        recyclerView = findViewById(R.id.historyList)
        emptyView = findViewById(R.id.emptyHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        showHistory()
    }

    private fun showHistory() {
        val records = HistoryRepository.load(this)
        recyclerView.adapter = HistoryAdapter(records)
        val isEmpty = records.isEmpty()
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}
