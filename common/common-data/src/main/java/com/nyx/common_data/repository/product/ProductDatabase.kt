package com.nyx.common_data.repository.product

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nyx.common_data.converters.Converters
import com.nyx.common_data.repository.product.room.ProductDaoRoomImpl
import com.nyx.common_data.repository.product.room.entities.ProductRoomEntity

@Database(entities = [ProductRoomEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDaoRoomImpl

    companion object {

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ProductDatabase::class.java, "products.db"
            ).build()
        }
    }
}