package com.fatmawati.noteproductapp.ui.view

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.data.model.Product
import com.fatmawati.noteproductapp.ui.adapter.ProductAdapter
import com.fatmawati.noteproductapp.ui.viewmodel.ApiResult
import com.fatmawati.noteproductapp.ui.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    private var currentContextMenuPosition: Int = RecyclerView.NO_POSITION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_products)
        progressBar = findViewById(R.id.progress_bar)
        errorTextView = findViewById(R.id.text_error)

        setupRecyclerView()
        observeViewModel()

        viewModel.fetchProducts() // Panggil fetch produk

        val fab: FloatingActionButton = findViewById(R.id.fab_add_product)
        fab.setOnClickListener {
            startActivity(Intent(this, AddEditProductActivity::class.java))
        }

        registerForContextMenu(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java)) // Pastikan CartActivity ada
                true
            }
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java)) // Pastikan HistoryActivity ada
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter { product ->
            viewModel.addItemToCart(product.id)
            Toast.makeText(this, "${product.name} ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
        }
        recyclerView.apply {
            adapter = productAdapter
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayoutManager

            val dividerItemDecoration = DividerItemDecoration(
                context,
                linearLayoutManager.orientation
            )
            addItemDecoration(dividerItemDecoration)
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

    fun setCurrentContextMenuPosition(position: Int) {
        currentContextMenuPosition = position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (currentContextMenuPosition != RecyclerView.NO_POSITION && currentContextMenuPosition < productAdapter.itemCount) {
            val product: Product = productAdapter.currentList[currentContextMenuPosition]

            return when (item.itemId) {
                R.id.menu_edit -> {
                    val intent = Intent(this, AddEditProductActivity::class.java)
                    intent.putExtra("PRODUCT_ID", product.id)
                    startActivity(intent)
                    resetContextMenuPosition()
                    true
                }
                R.id.menu_delete -> {
                    AlertDialog.Builder(this)
                        .setTitle("Hapus Produk")
                        .setMessage("Anda yakin ingin menghapus ${product.name}?")
                        .setPositiveButton("Ya") { _, _ ->
                            viewModel.deleteProduct(product.id)
                        }
                        .setNegativeButton("Tidak", null)
                        .show()
                    resetContextMenuPosition()
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun resetContextMenuPosition() {
        currentContextMenuPosition = RecyclerView.NO_POSITION
    }
}
