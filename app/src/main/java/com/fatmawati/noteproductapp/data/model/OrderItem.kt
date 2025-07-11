package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Merepresentasikan satu baris item di dalam sebuah pesanan,
 * dari tabel 'order_items'.
 */
data class OrderItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("product_name")
    val productName: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("price")
    val price: BigDecimal
)