package com.fatmawati.noteproductapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatmawati.noteproductapp.data.model.CreateProductRequest
import com.fatmawati.noteproductapp.data.model.Product
import com.fatmawati.noteproductapp.data.repository.AppRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _products = MutableLiveData<ApiResult<List<Product>>>()
    val products: LiveData<ApiResult<List<Product>>> = _products

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = ApiResult.Loading
            try {
                val response = repository.getProducts()
                if (response.isSuccessful && response.body() != null) {
                    _products.value = ApiResult.Success(response.body()!!)
                } else {
                    _products.value = ApiResult.Error("Gagal memuat produk: ${response.message()}")
                }
            } catch (e: Exception) {
                _products.value = ApiResult.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun addItemToCart(productId: Int) {
        viewModelScope.launch {
            try {
                repository.addToCart(productId, 1)
            } catch (e: Exception) {
                //
            }
        }
    }

    fun createNewProduct(name: String, price: BigDecimal, description: String) {
        viewModelScope.launch {
            val request = CreateProductRequest(name, price, description)
            try {
                val response = repository.createProduct(request)
                if (response.isSuccessful) {
                    fetchProducts()
                } else {
                    //
                }
            } catch (e: Exception) {
                //
            }
        }
    }
}