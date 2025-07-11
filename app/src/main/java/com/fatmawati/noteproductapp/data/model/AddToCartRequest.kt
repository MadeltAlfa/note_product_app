package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class to represent the request body for adding an item to the cart.
 * It will be serialized into a JSON object like: {"product_id": 1, "quantity": 1}
 */
data class AddToCartRequest(
    @SerializedName("product_id")
    val productId: Int,

    @SerializedName("quantity")
    val quantity: Int = 1
)