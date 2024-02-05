package com.nyx.profile_compose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common.views.ButtonItemView
import com.nyx.common.views.ScreenTitleView
import com.nyx.common.views.VerticalSpacer
import com.nyx.profile_api.navigation.ProfileScreenNavigation
import com.nyx.profile_compose.navigation.profileActionNavigation
import com.nyx.profile_impl.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    screenNavigation: ProfileScreenNavigation,
) {

    val viewState = viewModel.viewStates().observeAsState().value

    ProfileView()

    profileActionNavigation(viewModel = viewModel, screenNavigation = screenNavigation)
}

@Composable
private fun ProfileView() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            ScreenTitleView(text = "Личный кабинет")
            VerticalSpacer(height = 8.dp)
            UserNameItemView(username = "Name Surname", phone = "+7 123 456 78 99")
            VerticalSpacer(height = 20.dp)
            FavouritesItemView(productsCount = 1)
            VerticalSpacer(height = 8.dp)
            ShopsItemView()
            VerticalSpacer(height = 8.dp)
            FeedbackItemView()
            VerticalSpacer(height = 8.dp)
            OfferItemView()
            VerticalSpacer(height = 8.dp)
            ReturnProductItemView()
        }

        ExitButton(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp))
    }
}

@Composable
private fun UserNameItemView(username: String, phone: String) {
    ButtonItemView(
        leadingIcon = Icons.Default.Face,
        title = username,
        subtitle = phone,
        trailingIcon = Icons.Default.ExitToApp,
        onClick = {})
}

@Composable
private fun FavouritesItemView(productsCount: Int) {
    ButtonItemView(
        leadingIcon = Icons.Default.FavoriteBorder,
        title = "Избранное",
        subtitle = "$productsCount товар",
        onClick = {})
}

@Composable
private fun ShopsItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.ShoppingCart,
        title = "Магазины",
        onClick = {})
}

@Composable
private fun FeedbackItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.Email,
        title = "Обратная связь",
        onClick = {}
    )
}

@Composable
private fun OfferItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.List,
        title = "Оферта",
        onClick = {}
    )
}

@Composable
private fun ReturnProductItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.ArrowForward,
        title = "Возврат товара",
        onClick = {}
    )
}

@Composable
private fun ExitButton(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        onClick = { /*TODO*/ }) {
        Text(text = "Выйти", textAlign = TextAlign.Center)
    }
}