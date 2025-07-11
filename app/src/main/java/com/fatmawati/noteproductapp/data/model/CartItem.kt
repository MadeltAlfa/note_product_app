package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Data class representing a single item in the shopping cart,
 * as returned by the GET /api/cart endpoint.
 */
data class CartItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: BigDecimal,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("total_price")
    val totalPrice: BigDecimal
)