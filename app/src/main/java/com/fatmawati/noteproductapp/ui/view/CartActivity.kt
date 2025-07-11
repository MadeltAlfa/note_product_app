package com.fatmawati.noteproductapp.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.ui.adapter.CartAdapter
import com.fatmawati.noteproductapp.ui.viewmodel.ApiResult
import com.fatmawati.noteproductapp.ui.viewmodel.CartViewModel
import com.fatmawati.noteproductapp.util.toRupiahFormat

class CartActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var checkoutButton: Button
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        supportActionBar?.title = "Keranjang Belanja"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recycler_view_cart)
        progressBar = findViewById(R.id.progress_bar_cart)
        checkoutButton = findViewById(R.id.btn_checkout)
        totalTextView = findViewById(R.id.text_total_price)

        setupRecyclerView()
        observeViewModel()

        checkoutButton.setOnClickListener {
            viewModel.performCheckout()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchCartItems()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter { cartItem ->
            Toast.makeText(this, "${cartItem.name} dihapus", Toast.LENGTH_SHORT).show()
             viewModel.removeFromCart(cartItem.id)
        }
        recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.cartItems.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                is ApiResult.Success -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    cartAdapter.submitList(result.data)
                    totalTextView.visibility = if (result.data.isNotEmpty()) View.VISIBLE else View.GONE
                    checkoutButton.isEnabled = result.data.isNotEmpty()
                }
                is ApiResult.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.cartTotal.observe(this, Observer { total ->
            totalTextView.text = "Total: Rp ${total.toRupiahFormat()}"
        })

        viewModel.checkoutStatus.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Loading -> {
                    checkoutButton.isEnabled = false
                    progressBar.visibility = View.VISIBLE
                }
                is ApiResult.Success -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Checkout berhasil! Order ID: ${result.data.orderId}", Toast.LENGTH_LONG).show()
                    finish()
                }
                is ApiResult.Error -> {
                    progressBar.visibility = View.GONE
                    checkoutButton.isEnabled = true
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