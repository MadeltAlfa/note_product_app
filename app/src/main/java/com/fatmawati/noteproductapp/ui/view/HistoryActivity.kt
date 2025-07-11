package com.fatmawati.noteproductapp.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.ui.adapter.HistoryAdapter
import com.fatmawati.noteproductapp.ui.viewmodel.ApiResult
import com.fatmawati.noteproductapp.ui.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.title = "Riwayat Pesanan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recycler_view_history)
        progressBar = findViewById(R.id.progress_bar_history)
        errorTextView = findViewById(R.id.text_error_history)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchOrders()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter { order ->
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("ORDER_ID", order.id)
            startActivity(intent)
        }
        recyclerView.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(this@HistoryActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.orders.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is ApiResult.Success -> {
                    progressBar.visibility = View.GONE
                    if (result.data.isEmpty()) {
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = "Belum ada riwayat pesanan."
                    } else {
                        errorTextView.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        historyAdapter.submitList(result.data)
                    }
                }
                is ApiResult.Error -> {
                    progressBar.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = result.message
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}