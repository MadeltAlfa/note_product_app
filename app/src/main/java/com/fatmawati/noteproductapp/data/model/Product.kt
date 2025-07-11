package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Merepresentasikan objek produk dari tabel 'products'.
 */
data class Product(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: BigDecimal,

    @SerializedName("description")
    val description: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)