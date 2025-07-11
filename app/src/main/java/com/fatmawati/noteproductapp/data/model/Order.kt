package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Merepresentasikan objek pesanan dari tabel 'orders'.
 */
data class Order(
    @SerializedName("id")
    val id: Int,

    @SerializedName("total")
    val total: BigDecimal,

    @SerializedName("created_at")
    val createdAt: String,

    // Properti ini diisi saat memanggil endpoint detail order (/api/orders/:id)
    // dan bersifat nullable karena tidak ada di endpoint daftar order (/api/orders).
    @SerializedName("items")
    val items: List<OrderItem>? = null
)