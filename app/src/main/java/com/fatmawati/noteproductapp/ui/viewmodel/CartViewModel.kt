package com.fatmawati.noteproductapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatmawati.noteproductapp.data.model.CartItem
import com.fatmawati.noteproductapp.data.model.CheckoutResponse
import com.fatmawati.noteproductapp.data.repository.AppRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CartViewModel : ViewModel() {

    private val repository = AppRepository()

    private val _cartItems = MutableLiveData<ApiResult<List<CartItem>>>()
    val cartItems: LiveData<ApiResult<List<CartItem>>> = _cartItems

    private val _cartTotal = MutableLiveData<BigDecimal>()
    val cartTotal: LiveData<BigDecimal> = _cartTotal

    private val _checkoutStatus = MutableLiveData<ApiResult<CheckoutResponse>>()
    val checkoutStatus: LiveData<ApiResult<CheckoutResponse>> = _checkoutStatus

    fun fetchCartItems() {
        viewModelScope.launch {
            _cartItems.value = ApiResult.Loading
            try {
                val response = repository.getCart()
                if (response.isSuccessful) {
                    val items = response.body() ?: emptyList()
                    _cartItems.value = ApiResult.Success(items)

                    val total = items.sumOf { it.totalPrice }
                    _cartTotal.value = total
                } else {
                    _cartItems.value = ApiResult.Error("Gagal memuat keranjang: ${response.message()}")
                }
            } catch (e: Exception) {
                _cartItems.value = ApiResult.Error(e.message ?: "Terjadi kesalahan tidak diketahui")
            }
        }
    }

    fun removeFromCart(cartItemId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.removeFromCart(cartItemId)
                if (response.isSuccessful) {
                    fetchCartItems()
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }

    fun performCheckout() {
        viewModelScope.launch {
            _checkoutStatus.value = ApiResult.Loading
            try {
                val response = repository.checkout()
                if (response.isSuccessful && response.body() != null) {
                    _checkoutStatus.value = ApiResult.Success(response.body()!!)
                    fetchCartItems()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Gagal checkout"
                    _checkoutStatus.value = ApiResult.Error(errorMessage)
                }
            } catch (e: Exception) {
                _checkoutStatus.value = ApiResult.Error(e.message ?: "Terjadi kesalahan koneksi")
            }
        }
    }
}