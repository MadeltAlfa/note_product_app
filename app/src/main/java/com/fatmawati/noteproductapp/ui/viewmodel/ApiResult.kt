package com.fatmawati.noteproductapp.ui.viewmodel

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()

    data class Error(val message: String) : ApiResult<Nothing>()

    object Loading : ApiResult<Nothing>()
}