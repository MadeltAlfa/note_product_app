package com.fatmawati.noteproductapp.data.remote

import com.fatmawati.noteproductapp.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface untuk Retrofit yang mendefinisikan semua endpoint API.
 * Menggunakan suspend functions untuk integrasi yang mulus dengan Coroutines.
 */
interface ApiService {

    // --- Products ---
    @GET("/api/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("/api/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>

    @POST("/api/products")
    suspend fun createProduct(@Body productRequest: CreateProductRequest): Response<Product>

    @PUT("/api/products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body productRequest: CreateProductRequest
    ): Response<Product>

    @DELETE("/api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<MessageResponse>

    // --- Cart ---
    @GET("/api/cart")
    suspend fun getCart(): Response<List<CartItem>>

    @POST("/api/cart")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<MessageResponse>

    @DELETE("/api/cart/{id}")
    suspend fun removeFromCart(@Path("id") cartItemId: Int): Response<MessageResponse>

    @POST("/api/cart/checkout")
    suspend fun checkout(): Response<CheckoutResponse>

    // --- Orders ---
    @GET("/api/orders")
    suspend fun getOrders(): Response<List<Order>>

    @GET("/api/orders/{id}")
    suspend fun getOrderDetails(@Path("id") orderId: Int): Response<Order>

    @DELETE("/api/orders")
    suspend fun clearOrderHistory(): Response<MessageResponse>
}