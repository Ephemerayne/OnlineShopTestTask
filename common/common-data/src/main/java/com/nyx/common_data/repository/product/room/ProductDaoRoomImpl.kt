package com.nyx.common_data.repository.product.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyx.common_data.repository.product.room.entities.ProductRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDaoRoomImpl: ProductDao {

    @Query("SELECT * FROM product_table")
    override fun getProducts(): Flow<List<ProductRoomEntity>>

    @Query("SELECT * FROM product_table WHERE roomId=:id")
    override fun getProduct(id: String): Flow<ProductRoomEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertProducts(products: List<ProductRoomEntity>)
}