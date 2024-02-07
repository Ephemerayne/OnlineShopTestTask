package com.nyx.common_data.models

import com.nyx.common_api.models.ProductEntity

data class ProductsResponse(
    val items: List<ProductEntity>,
)
