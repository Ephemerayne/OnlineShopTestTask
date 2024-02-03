package com.nyx.catalog_api.models

data class ProductModel(
    val id: Int = 0,
    val title: String? = null,
    val subtitle: String? = null,
    val price: Price? = null,
    val feedback: Feedback? = null,
    val tags: List<String>? = null,
    val available: Int? = null,
    val description: String? = null,
    val info: List<Info>? = null,
    val ingredients: String? = null,
)

data class Price(
    val price: Double? = null,
    val discount: Int? = null,
    val priceWithDiscount: Double? = null,
    val unit: String? = null,
)

data class Feedback(
    val count: Int? = null,
    val rating: Double? = null,
)

data class Info(
    val title: String? = null,
    val value: String? = null,
)