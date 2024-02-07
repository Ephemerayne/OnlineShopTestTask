package com.nyx.common_api.models

data class ProductEntity(
    val id: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val price: PriceEntity? = null,
    val feedback: FeedbackEntity? = null,
    val tags: List<String>? = null,
    val available: Int? = null,
    val description: String? = null,
    val info: List<InfoEntity>? = null,
    val ingredients: String? = null,
)

data class PriceEntity(
    val price: String? = null,
    val discount: Int? = null,
    val priceWithDiscount: String? = null,
    val unit: String? = null,
) {
    companion object {
        val empty: PriceEntity
            get() = PriceEntity(
                price = "null",
                discount = 0,
                priceWithDiscount = "null",
                unit = "null"
            )
    }
}

data class FeedbackEntity(
    val count: Int? = null,
    val rating: Double? = null,
) {
    companion object {
        val empty: FeedbackEntity
            get() = FeedbackEntity(
                count = 0,
                rating = 0.0
            )
    }
}

data class InfoEntity(
    val title: String? = null,
    val value: String? = null,
) {
    companion object {
        val empty: InfoEntity
            get() = InfoEntity(
                title = "null",
                value = "null"
            )
    }
}