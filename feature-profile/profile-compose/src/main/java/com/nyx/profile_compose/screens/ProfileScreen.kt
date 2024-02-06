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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.common_compose.views.ButtonItemView
import com.nyx.common_compose.views.ScreenTitleView
import com.nyx.common_compose.views.VerticalSpacer
import com.nyx.profile_api.navigation.ProfileScreenNavigation
import com.nyx.profile_compose.R
import com.nyx.profile_compose.navigation.profileActionNavigation
import com.nyx.profile_impl.ProfileViewModel
import com.nyx.profile_impl.models.ProfileViewEvent
import com.nyx.common_compose.R as ColorRes

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    screenNavigation: ProfileScreenNavigation,
) {

    val viewState = viewModel.viewStates().observeAsState().value

    val onFavouritesItemClick = viewModel.rememberEvent(ProfileViewEvent.OnFavouritesClicked)

    ProfileView(
        onFavouritesClick = onFavouritesItemClick,
        onExitClick = {} // exit to registration screen
    )

    profileActionNavigation(viewModel = viewModel, screenNavigation = screenNavigation)
}

@Composable
private fun ProfileView(
    onFavouritesClick: () -> Unit,
    onExitClick: () -> Unit,
) {
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
            ScreenTitleView(text = stringResource(R.string.profile_title))
            VerticalSpacer(height = 8.dp)
            UserNameItemView(username = "Name Surname", phone = "+7 123 456 78 99")
            VerticalSpacer(height = 20.dp)
            FavouritesItemView(
                productsCount = 30,
                onFavouritesClick = onFavouritesClick
            )
            VerticalSpacer(height = 8.dp)
            ShopsItemView()
            VerticalSpacer(height = 8.dp)
            FeedbackItemView()
            VerticalSpacer(height = 8.dp)
            OfferItemView()
            VerticalSpacer(height = 8.dp)
            ReturnProductItemView()
        }

        ExitButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            onExitClick = onExitClick
        )
    }
}

@Composable
private fun UserNameItemView(username: String, phone: String) {
    ButtonItemView(
        leadingIcon = Icons.Default.Face,
        title = username,
        subtitle = phone,
        trailingIcon = Icons.Default.ExitToApp,
        onClick = { /* No implementation */ })
}

@Composable
private fun FavouritesItemView(productsCount: Int, onFavouritesClick: () -> Unit) {
    ButtonItemView(
        leadingIcon = Icons.Default.FavoriteBorder,
        title = stringResource(R.string.profile_favourite_item_text),
        subtitle = pluralStringResource(R.plurals.favourite_products, productsCount, productsCount),
        onClick = onFavouritesClick
    )
}

@Composable
private fun ShopsItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.ShoppingCart,
        title = stringResource(R.string.profile_shops_item_text),
        onClick = { /* No implementation */ })
}

@Composable
private fun FeedbackItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.Email,
        title = stringResource(R.string.profile_feedback_item_text),
        onClick = { /* No implementation */ }
    )
}

@Composable
private fun OfferItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.List,
        title = stringResource(R.string.profile_offer_item_text),
        onClick = { /* No implementation */ }
    )
}

@Composable
private fun ReturnProductItemView() {
    ButtonItemView(
        leadingIcon = Icons.Default.ArrowForward,
        title = stringResource(R.string.profile_return_product_item_text),
        onClick = { /* No implementation */ }
    )
}

@Composable
private fun ExitButton(modifier: Modifier = Modifier, onExitClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = ColorRes.color.background_light_gray)
        ),
        elevation = null,
        onClick = onExitClick
    ) {
        Text(
            text = stringResource(R.string.exit_button_text),
            textAlign = TextAlign.Center,
            style = AppTypography.buttonText2
        )
    }
}