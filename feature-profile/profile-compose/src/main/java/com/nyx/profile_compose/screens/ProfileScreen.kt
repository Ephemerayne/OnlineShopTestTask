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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_api.models.UserEntity
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
import com.nyx.common_compose.R as CommonRes

@Composable
fun ProfileScreen(
    screenNavigation: ProfileScreenNavigation,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onFavouritesItemClick = viewModel.rememberEvent(ProfileViewEvent.OnFavouritesClicked)

    ProfileView(
        productCount = viewState.productCount,
        userEntity = viewState.userEntity,
        onFavouritesClick = onFavouritesItemClick,
        onExitClick = {} // exit to registration screen
    )

    profileActionNavigation(viewModel = viewModel, screenNavigation = screenNavigation)
}

@Composable
private fun ProfileView(
    productCount: Int,
    userEntity: UserEntity?,
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
            UserNameItemView(
                username = "${userEntity?.name.orEmpty()} ${userEntity?.surname.orEmpty()}",
                phone = "+7 ${userEntity?.phoneNumber.orEmpty()}"
            )
            VerticalSpacer(height = 20.dp)
            FavouritesItemView(
                productsCount = productCount,
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
        leadingIcon = painterResource(id = CommonRes.drawable.profile_icon),
        title = username,
        subtitle = phone,
        trailingIcon = painterResource(id = R.drawable.exit_icon),
        onClick = { /* No implementation */ })
}

@Composable
private fun FavouritesItemView(productsCount: Int, onFavouritesClick: () -> Unit) {
    ButtonItemView(
        leadingIcon = painterResource(CommonRes.drawable.favourite_icon),
        title = stringResource(R.string.profile_favourite_item_text),
        subtitle = pluralStringResource(R.plurals.favourite_products, productsCount, productsCount),
        onClick = onFavouritesClick
    )
}

@Composable
private fun ShopsItemView() {
    ButtonItemView(
        leadingIcon = painterResource(R.drawable.shop_icon),
        title = stringResource(R.string.profile_shops_item_text),
        onClick = { /* No implementation */ })
}

@Composable
private fun FeedbackItemView() {
    ButtonItemView(
        leadingIcon = painterResource(R.drawable.feedback_icon),
        title = stringResource(R.string.profile_feedback_item_text),
        onClick = { /* No implementation */ }
    )
}

@Composable
private fun OfferItemView() {
    ButtonItemView(
        leadingIcon = painterResource(R.drawable.offer_icon),
        title = stringResource(R.string.profile_offer_item_text),
        onClick = { /* No implementation */ }
    )
}

@Composable
private fun ReturnProductItemView() {
    ButtonItemView(
        leadingIcon = painterResource(R.drawable.return_icon),
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