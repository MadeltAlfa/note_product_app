package com.fatmawati.noteproductapp.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.fatmawati.noteproductapp.R
import com.fatmawati.noteproductapp.ui.viewmodel.MainViewModel
import java.math.BigDecimal

class AddEditProductActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var nameEditText: TextInputEditText
    private lateinit var priceEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var saveButton: Button
    private var currentProductId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_product)

        nameEditText = findViewById(R.id.edit_text_product_name)
        priceEditText = findViewById(R.id.edit_text_product_price)
        descriptionEditText = findViewById(R.id.edit_text_product_description)
        saveButton = findViewById(R.id.btn_save_product)

        if (intent.hasExtra("PRODUCT_ID")) {
            currentProductId = intent.getIntExtra("PRODUCT_ID", -1)
            supportActionBar?.title = "Edit Produk"
        } else {
            supportActionBar?.title = "Tambah Produk Baru"
        }

        saveButton.setOnClickListener { saveProduct() }
    }

    private fun saveProduct() {
        val name = nameEditText.text.toString()
        val priceString = priceEditText.text.toString()
        val description = descriptionEditText.text.toString()

        if (name.isBlank() || priceString.isBlank()) {
            Toast.makeText(this, "Nama dan Harga wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val price = BigDecimal(priceString)

        if (currentProductId == null) {
            viewModel.createNewProduct(name, price, description)
        } else {
            //
        }
        Toast.makeText(this, "Produk disimpan", Toast.LENGTH_SHORT).show()
        finish()
    }
}