package com.fatmawati.noteproductapp.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun BigDecimal.toRupiahFormat(): String {
    val symbols = DecimalFormatSymbols(Locale("in", "ID"))
    symbols.groupingSeparator = '.'
    symbols.decimalSeparator = ','

    val formatter = DecimalFormat("#,###", symbols)

    return "Rp${formatter.format(this)}"
}