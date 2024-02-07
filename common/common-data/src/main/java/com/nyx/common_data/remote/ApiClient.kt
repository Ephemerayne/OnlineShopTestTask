package com.nyx.common_data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}

object ApiClient {
    val productService: ProductService by lazy {
        RetrofitClient.retrofit.create(ProductService::class.java)
    }
}