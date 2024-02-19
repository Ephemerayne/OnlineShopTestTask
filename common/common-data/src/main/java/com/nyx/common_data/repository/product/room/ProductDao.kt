package com.nyx.common_data.repository.product.room

import com.nyx.common_data.repository.product.room.entities.ProductRoomEntity
import kotlinx.coroutines.flow.Flow

interface ProductDao {
    fun getProducts(): Flow<List<ProductRoomEntity>>
    fun getProduct(id: String): Flow<ProductRoomEntity?>
    fun insertProducts(products: List<ProductRoomEntity>)
}