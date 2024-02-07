package com.nyx.common_data.remote

import com.nyx.common_data.models.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProductService {
    @GET("/v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    @Headers("Content-Type: application/json")
    suspend fun getProducts(): Response<ProductsResponse>
}