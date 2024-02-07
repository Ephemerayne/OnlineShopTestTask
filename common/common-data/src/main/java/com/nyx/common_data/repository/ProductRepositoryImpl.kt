package com.nyx.common_data.repository

import com.nyx.common_api.models.ProductEntity
import com.nyx.common_api.repository.ProductRepository
import com.nyx.common_data.remote.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl : ProductRepository {

    private val service = ApiClient.productService

    override fun getProducts(): Flow<List<ProductEntity>> = flow {
        val products = service.getProducts()
        products.body()?.let { emit(it.items) }
    }

    override fun getFavourite(): Flow<List<ProductEntity>> {
        TODO("Not yet implemented")
    }

    override fun addFavourite(id: String) {
        TODO("Not yet implemented")
    }

    override fun deleteFavourite(id: String) {
        TODO("Not yet implemented")
    }

    override fun getProduct(id: String):Flow<ProductEntity> {
        TODO("Not yet implemented")
    }
}