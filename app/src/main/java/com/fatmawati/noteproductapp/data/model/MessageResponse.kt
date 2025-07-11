package com.fatmawati.noteproductapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class generik untuk menangani respons API yang hanya berisi pesan.
 * Contoh: {"message": "Product deleted successfully"}
 */
data class MessageResponse(
    @SerializedName("message")
    val message: String
)