package com.fatmawati.noteproductapp.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.ui.adapter.ProductAdapter
import com.fatmawati.noteproductapp.ui.viewmodel.ApiResult
import com.fatmawati.noteproductapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_products)
        progressBar = findViewById(R.id.progress_bar)
        errorTextView = findViewById(R.id.text_error)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchProducts()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            viewModel.addItemToCart(product.id)
            Toast.makeText(this, "${product.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }
        recyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.products.observe(this, Observer { result ->
            when (result) {
                is ApiResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                }
                is ApiResult.Success -> {
                    progressBar.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    productAdapter.submitList(result.data)
                }
                is ApiResult.Error -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = result.message
                }
            }
        })
    }
}