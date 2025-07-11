package com.fatmawati.noteproductapp.data.repository

import com.fatmawati.noteproductapp.data.model.AddToCartRequest
import com.fatmawati.noteproductapp.data.model.CreateProductRequest
import com.fatmawati.noteproductapp.data.remote.RetrofitInstance

/**
 * Repository yang mengelola data untuk aplikasi.
 *
 * Repository ini bertanggung jawab untuk mengabstraksi sumber data dari seluruh aplikasi.
 * ViewModel akan berkomunikasi dengan Repository ini, bukan langsung ke ApiService.
 * Ini mempermudah pengujian dan pengelolaan data (misalnya, menambahkan caching di masa depan).
 */
class AppRepository {

    private val apiService = RetrofitInstance.api

    // --- Products ---
    suspend fun getProducts() = apiService.getProducts()
    suspend fun getProductById(id: Int) = apiService.getProductById(id)
    suspend fun createProduct(productRequest: CreateProductRequest) = apiService.createProduct(productRequest)
    suspend fun updateProduct(id: Int, productRequest: CreateProductRequest) = apiService.updateProduct(id, productRequest)
    suspend fun deleteProduct(id: Int) = apiService.deleteProduct(id)

    // --- Cart ---
    suspend fun getCart() = apiService.getCart()
    suspend fun addToCart(productId: Int, quantity: Int) = apiService.addToCart(AddToCartRequest(productId, quantity))
    suspend fun removeFromCart(cartItemId: Int) = apiService.removeFromCart(cartItemId)
    suspend fun checkout() = apiService.checkout()

    // --- Orders ---
    suspend fun getOrders() = apiService.getOrders()
    suspend fun getOrderDetails(orderId: Int) = apiService.getOrderDetails(orderId)
    suspend fun clearOrderHistory() = apiService.clearOrderHistory()
}