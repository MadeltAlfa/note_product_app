package com.fatmawati.noteproductapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object untuk menyediakan instance Retrofit yang telah dikonfigurasi.
 */
object RetrofitInstance {

    // ⚠️ PENTING:
    // Gunakan "http://10.0.2.2:3000/" jika menjalankan di Android Emulator.
    // Ganti dengan alamat IP lokal komputer Anda (misal: "http://192.168.1.5:3000/")
    // jika menjalankan di perangkat Android fisik yang terhubung ke WiFi yang sama.
    private const val BASE_URL = "http://10.0.2.2:3000/"

    // Interceptor untuk logging (opsional, tapi sangat membantu untuk debugging)
    // Ini akan menampilkan detail request dan response di Logcat.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Client OkHttp yang akan digunakan oleh Retrofit
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Instance Retrofit yang dibuat secara lazy (hanya saat pertama kali diakses).
     */
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Menggunakan Gson untuk konversi JSON
            .build()
            .create(ApiService::class.java)
    }
}