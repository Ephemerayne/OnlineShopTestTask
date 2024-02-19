package com.nyx.common_api.repository.product

import com.nyx.common_api.models.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<List<ProductEntity>>
    suspend fun getFavourite(): Flow<List<ProductEntity>>
    fun addFavourite(id: String)
    fun deleteFavourite(id: String)
    suspend fun getProduct(id: String): Flow<ProductEntity?>
}