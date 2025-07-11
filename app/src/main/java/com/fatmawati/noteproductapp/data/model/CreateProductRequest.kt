package com.fatmawati.noteproductapp.data.model

import java.math.BigDecimal

/**
 * Data class to represent the request body for creating or updating a product.
 */
data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,
    val description: String?
)