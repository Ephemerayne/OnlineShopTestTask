package com.nyx.common_api.repository

import com.nyx.common_api.models.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<ProductEntity>>
    fun getFavourite(): Flow<List<ProductEntity>>
    fun addFavourite(id: String)
    fun deleteFavourite(id: String)
    fun getProduct(id: String): Flow<ProductEntity?>
}