package com.nyx.common_data.repository.product.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_table",
    indices = [
        Index(value = ["id"], unique = true)
    ]
)

data class ProductRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int = 0,

    val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<Info>,
    val ingredients: String,
)

data class Price(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String,
)

data class Feedback(
    val count: Int,
    val rating: Double,
)

data class Info(
    val title: String,
    val value: String,
)
