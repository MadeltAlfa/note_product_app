package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Data class representing the successful response after a checkout.
 */
data class CheckoutResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("orderId")
    val orderId: Int,

    @SerializedName("total")
    val total: BigDecimal
)