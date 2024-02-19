package com.nyx.common_data.repository.product.room.mappers

import com.nyx.common_api.models.FeedbackEntity
import com.nyx.common_api.models.InfoEntity
import com.nyx.common_api.models.PriceEntity
import com.nyx.common_api.models.ProductEntity
import com.nyx.common_data.repository.product.room.entities.Feedback
import com.nyx.common_data.repository.product.room.entities.Info
import com.nyx.common_data.repository.product.room.entities.Price
import com.nyx.common_data.repository.product.room.entities.ProductRoomEntity

fun ProductEntity.toRoomEntity(): ProductRoomEntity {
    return ProductRoomEntity(
        id = id.toString(),
        title = title.toString(),
        subtitle = subtitle.toString(),
        price = (price ?: PriceEntity.empty).toRoomEntity(),
        feedback = (feedback ?: FeedbackEntity.empty).toRoomEntity(),
        tags = tags ?: emptyList(),
        available = available ?: 0,
        description = description.toString(),
        info = (info ?: emptyList()).map { it.toRoomEntity() },
        ingredients = ingredients.toString()
    )
}

fun PriceEntity.toRoomEntity(): Price =
    Price(
        price = price.toString(),
        discount = discount ?: 0,
        priceWithDiscount = priceWithDiscount.toString(),
        unit = unit.toString()
    )

fun FeedbackEntity.toRoomEntity(): Feedback =
    Feedback(
        count = count ?: 0,
        rating = rating ?: 0.0
    )

fun InfoEntity.toRoomEntity(): Info =
    Info(
        title = title.toString(),
        value = value.toString()
    )

fun ProductRoomEntity.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        subtitle = subtitle,
        price = price.toPriceEntity(),
        feedback = feedback.toFeedbackEntity(),
        tags = tags,
        available = available,
        description = description,
        info = info.map { it.toInfoEntity() },
        ingredients = ingredients
    )
}

fun Price.toPriceEntity(): PriceEntity =
    PriceEntity(
        price = price,
        discount = discount,
        priceWithDiscount = priceWithDiscount,
        unit = unit
    )

fun Feedback.toFeedbackEntity(): FeedbackEntity =
    FeedbackEntity(
        count = count,
        rating = rating
    )

fun Info.toInfoEntity(): InfoEntity =
    InfoEntity(
        title = title,
        value = value
    )