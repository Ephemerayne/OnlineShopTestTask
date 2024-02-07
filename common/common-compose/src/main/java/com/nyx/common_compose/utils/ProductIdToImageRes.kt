package com.nyx.common_compose.utils

import com.nyx.common_compose.R

fun productIdToImageRes(id: String): List<Int> = when (id) {
    "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> listOf(
        R.drawable.product_img_1,
        R.drawable.product_img_2
    )

    "54a876a5-2205-48ba-9498-cfecff4baa6e" -> listOf(
        R.drawable.product_img_3,
        R.drawable.product_img_4
    )

    "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> listOf(
        R.drawable.product_img_2,
        R.drawable.product_img_1
    )

    "16f88865-ae74-4b7c-9d85-b68334bb97db" -> listOf(
        R.drawable.product_img_5,
        R.drawable.product_img_6
    )

    "26f88856-ae74-4b7c-9d85-b68334bb97db" -> listOf(
        R.drawable.product_img_4,
        R.drawable.product_img_5
    )

    "15f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
        R.drawable.product_img_1,
        R.drawable.product_img_3
    )

    "88f88865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
        R.drawable.product_img_6,
        R.drawable.product_img_5
    )

    "55f58865-ae74-4b7c-9d81-b78334bb97db" -> listOf(
        R.drawable.product_img_3,
        R.drawable.product_img_2
    )

    else -> listOf()
}