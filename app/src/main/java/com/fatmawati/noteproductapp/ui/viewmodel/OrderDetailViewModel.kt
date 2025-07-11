package com.fatmawati.noteproductapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatmawati.noteproductapp.data.model.Order
import com.fatmawati.noteproductapp.data.repository.AppRepository
import kotlinx.coroutines.launch

class OrderDetailViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _orderDetails = MutableLiveData<ApiResult<Order>>()
    val orderDetails: LiveData<ApiResult<Order>> = _orderDetails

    fun fetchOrderDetails(orderId: Int) {
        viewModelScope.launch {
            _orderDetails.value = ApiResult.Loading
            try {
                val response = repository.getOrderDetails(orderId)
                if (response.isSuccessful && response.body() != null) {
                    _orderDetails.value = ApiResult.Success(response.body()!!)
                } else {
                    _orderDetails.value = ApiResult.Error("Gagal memuat detail pesanan")
                }
            } catch (e: Exception) {
                _orderDetails.value = ApiResult.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}