package com.fatmawati.noteproductapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatmawati.noteproductapp.data.model.Order
import com.fatmawati.noteproductapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _orders = MutableLiveData<ApiResult<List<Order>>>()
    val orders: LiveData<ApiResult<List<Order>>> = _orders

    fun fetchOrders() {
        viewModelScope.launch {
            _orders.value = ApiResult.Loading
            try {
                val response = repository.getOrders()
                if (response.isSuccessful) {
                    _orders.value = ApiResult.Success(response.body() ?: emptyList())
                } else {
                    _orders.value = ApiResult.Error("Gagal memuat riwayat: ${response.message()}")
                }
            } catch (e: Exception) {
                _orders.value = ApiResult.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}