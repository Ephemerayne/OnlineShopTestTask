package com.nyx.common_compose.mappers

import com.nyx.common_api.models.FeedbackEntity
import com.nyx.common_api.models.InfoEntity
import com.nyx.common_api.models.PriceEntity
import com.nyx.common_api.models.ProductEntity
import com.nyx.common_compose.models.FeedbackUiEntity
import com.nyx.common_compose.models.InfoUiEntity
import com.nyx.common_compose.models.PriceUiEntity
import com.nyx.common_compose.models.ProductUiEntity

fun ProductEntity.toUiEntity(): ProductUiEntity {
    return ProductUiEntity(
        id = id.toString(),
        title = title.toString(),
        subtitle = subtitle.toString(),
        price = (price ?: PriceEntity.empty).toUiEntity(),
        feedback = (feedback ?: FeedbackEntity.empty).toUiEntity(),
        tags = tags ?: emptyList(),
        available = available ?: 0,
        description = description.toString(),
        info = (info ?: emptyList()).map { it.toUiEntity() },
        ingredients = ingredients.toString(),
    )
}

fun PriceEntity.toUiEntity(): PriceUiEntity =
    PriceUiEntity(
        price = price.toString(),
        discount = discount ?: 0,
        priceWithDiscount = priceWithDiscount.toString(),
        unit = unit.toString()
    )

fun FeedbackEntity.toUiEntity(): FeedbackUiEntity =
    FeedbackUiEntity(
        count = count ?: 0,
        rating = rating ?: 0.0
    )

fun InfoEntity.toUiEntity(): InfoUiEntity =
    InfoUiEntity(
        title = title.toString(),
        value = value.toString()
    )