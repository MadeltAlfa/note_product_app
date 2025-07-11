package com.fatmawati.noteproductapp.ui.view

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.ui.adapter.OrderItemAdapter
import com.fatmawati.noteproductapp.ui.viewmodel.ApiResult
import com.fatmawati.noteproductapp.ui.viewmodel.OrderDetailViewModel

class OrderDetailActivity : AppCompatActivity() {

    private val viewModel: OrderDetailViewModel by viewModels()
    private lateinit var orderIdTextView: TextView
    private lateinit var orderDateTextView: TextView
    private lateinit var orderTotalTextView: TextView

    private lateinit var orderItemAdapter: OrderItemAdapter
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        supportActionBar?.title = "Detail Pesanan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val orderId = intent.getIntExtra("ORDER_ID", -1)

        if (orderId == -1) {
            Toast.makeText(this, "ID Order tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        orderIdTextView = findViewById(R.id.text_detail_order_id)
        orderDateTextView = findViewById(R.id.text_detail_order_date)
        orderTotalTextView = findViewById(R.id.text_detail_order_total)
        itemsRecyclerView = findViewById(R.id.recycler_view_order_items)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchOrderDetails(orderId)
    }

    private fun setupRecyclerView() {
        orderItemAdapter = OrderItemAdapter()
        itemsRecyclerView.apply {
            adapter = orderItemAdapter
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.orderDetails.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Loading -> {
                }
                is ApiResult.Success -> {
                    val order = result.data

                    orderIdTextView.text = "Detail Order #${order.id}"
                    orderDateTextView.text = order.createdAt
                    orderTotalTextView.text = "Total: Rp ${order.total}"
                    orderItemAdapter.submitList(order.items ?: emptyList())
                }
                is ApiResult.Error -> {
                    Toast.makeText(this, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}