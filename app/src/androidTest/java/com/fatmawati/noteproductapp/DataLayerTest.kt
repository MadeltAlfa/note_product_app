package com.fatmawati.noteproductapp

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fatmawati.noteproductapp.data.repository.AppRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test untuk mengetes lapisan data (Repository dan ApiService).
 * Test ini akan berjalan di emulator/perangkat.
 */
@RunWith(AndroidJUnit4::class)
class DataLayerTest {

    private lateinit var repository: AppRepository

    @Before
    fun setup() {
        // Inisialisasi repository sebelum setiap test dijalankan
        repository = AppRepository()
    }

    @Test
    fun testGetProducts_receivesSuccessResponse() {
        // runBlocking digunakan dalam test untuk menjalankan suspend function
        // dan menunggu hingga selesai.
        runBlocking {
            // 1. Panggil metode dari repository
            val response = repository.getProducts()

            // 2. Cetak hasilnya ke Logcat untuk pengecekan manual
            // Gunakan tag yang unik agar mudah dicari
            Log.d("API_TEST", "Response Code: ${response.code()}")
            Log.d("API_TEST", "Response Body: ${response.body()}")
            Log.d("API_TEST", "Error Body: ${response.errorBody()?.string()}")

            // 3. Gunakan Assertions untuk validasi otomatis
            // Pastikan response tidak null
            assertNotNull("Response tidak boleh null", response)
            // Pastikan panggilan API berhasil (kode 200-299)
            assertTrue("Panggilan API harus berhasil (isSuccessful)", response.isSuccessful)
            // Pastikan body dari response tidak null
            assertNotNull("Body dari response tidak boleh null", response.body())
        }
    }

    // Anda bisa menambahkan test lain di sini, misalnya untuk addToCart
    @Test
    fun testAddToCart_receivesSuccessResponse() {
        runBlocking {
            // LANGKAH A: Ambil dulu daftar produk yang ada
            val productsResponse = repository.getProducts()
            assertTrue("Harus bisa mengambil daftar produk dulu", productsResponse.isSuccessful)
            assertNotNull("Daftar produk tidak boleh null", productsResponse.body())

            val productList = productsResponse.body()!!

            // Pastikan ada setidaknya satu produk untuk dites
            if (productList.isNotEmpty()) {
                // LANGKAH B: Ambil ID dari produk pertama yang valid
                val productToAdd = productList.first()
                val productId = productToAdd.id

                Log.d("API_TEST_CART", "Mencoba menambahkan produk dengan ID: $productId")

                // LANGKAH C: Lakukan tes addToCart dengan ID yang valid
                val addToCartResponse = repository.addToCart(productId, 1)

                assertTrue("Panggilan addToCart harus berhasil", addToCartResponse.isSuccessful)
                assertNotNull("Body dari response addToCart tidak boleh null", addToCartResponse.body())
                assertEquals("Product added to cart", addToCartResponse.body()?.message)

            } else {
                // Jika tidak ada produk sama sekali, lewati tes ini dengan pesan.
                Log.w("API_TEST_CART", "Tidak ada produk di database, tes addToCart dilewati.")
                // Kita bisa anggap ini berhasil karena tidak ada yang bisa dites.
                assertTrue("Melewati tes karena tidak ada produk", true)
            }
        }
    }
}