package com.nyx.common_compose.models

data class ProductUiEntity(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: PriceUiEntity,
    val feedback: FeedbackUiEntity,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoUiEntity>,
    val ingredients: String,
    val isFavourite: Boolean
)

data class PriceUiEntity(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String,
)

data class FeedbackUiEntity(
    val count: Int,
    val rating: Double,
)

data class InfoUiEntity(
    val title: String,
    val value: String,
)