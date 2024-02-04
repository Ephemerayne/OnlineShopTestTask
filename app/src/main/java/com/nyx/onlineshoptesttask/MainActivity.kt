package com.nyx.onlineshoptesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nyx.onlineshoptesttask.ui.theme.OnlineShopTestTaskTheme
import com.nyx.product_card_compose.screens.ProductCardScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnlineShopTestTaskTheme {
//                AppNavHost()
                ProductCardScreen()
            }
        }
    }
}