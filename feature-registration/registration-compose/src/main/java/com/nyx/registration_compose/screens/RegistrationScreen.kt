package com.nyx.registration_compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adeo.kviewmodel.compose.observeAsState
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_compose.utils.underlinedPartialText
import com.nyx.common_compose.viewmodel.rememberEvent
import com.nyx.common_compose.views.*
import com.nyx.registration_api.navigation.RegistrationScreenNavigation
import com.nyx.registration_compose.R
import com.nyx.registration_compose.navigation.registrationActionNavigation
import com.nyx.registration_impl.RegistrationViewModel
import com.nyx.registration_impl.models.registration.RegistrationViewEvent
import com.nyx.common_compose.R as CommonRes

@Composable
fun RegistrationScreen(
    screenNavigation: RegistrationScreenNavigation,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewStates().observeAsState().value

    val onNameChanged =
        viewModel.rememberEvent<String, _> { input ->
            RegistrationViewEvent.OnNameChanged(input)
        }

    val onSurnameChanged =
        viewModel.rememberEvent<String, _> { input ->
            RegistrationViewEvent.OnSurnameChanged(input)
        }

    val onPhoneNumberChanged =
        viewModel.rememberEvent<String, _> { input ->
            RegistrationViewEvent.OnPhoneNumberChanged(input)
        }

    val onClearNameClick =
        viewModel.rememberEvent(RegistrationViewEvent.OnClearNameClicked)
    val onClearSurnameClick =
        viewModel.rememberEvent(RegistrationViewEvent.OnClearSurnameClicked)
    val onClearPhoneNumberClick =
        viewModel.rememberEvent(RegistrationViewEvent.OnClearPhoneNumberClicked)

    val onEnterButtonClick = viewModel.rememberEvent(RegistrationViewEvent.OnEnterButtonClicked)

    if (viewState.isUserDataReceived) {
        RegistrationView(
            nameInput = viewState.name,
            surnameInput = viewState.surname,
            phoneNumberInput = viewState.phoneNumber,
            isEnterButtonEnabled = viewState.isInputValid,
            onNameChanged = onNameChanged,
            onSurnameChanged = onSurnameChanged,
            onPhoneNumberChanged = onPhoneNumberChanged,
            onClearNameClick = onClearNameClick,
            onClearSurnameClick = onClearSurnameClick,
            onClearPhoneNumberClick = onClearPhoneNumberClick,
            onEnterButtonClick = onEnterButtonClick
        )
    }

    registrationActionNavigation(viewModel = viewModel, navigation = screenNavigation)
}

@Composable
private fun RegistrationView(
    nameInput: String,
    surnameInput: String,
    phoneNumberInput: String,
    isEnterButtonEnabled: Boolean,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onClearNameClick: () -> Unit,
    onClearSurnameClick: () -> Unit,
    onClearPhoneNumberClick: () -> Unit,
    onEnterButtonClick: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    ScreenTitleView(text = stringResource(id = R.string.registration_enter_title))

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
        ) {
            CyrillicTextField(
                input = nameInput,
                placeholder = stringResource(R.string.placeholder_name),
                focusRequester = focusRequester,
                onTextChanged = onNameChanged,
                onClearInputClick = onClearNameClick
            )
            VerticalSpacer(height = 12.dp)
            CyrillicTextField(
                input = surnameInput,
                placeholder = stringResource(R.string.placeholder_surname),
                focusRequester = focusRequester,
                onTextChanged = onSurnameChanged,
                onClearInputClick = onClearSurnameClick
            )
            VerticalSpacer(height = 12.dp)
            PhoneNumberTextField(
                input = phoneNumberInput,
                placeholder = stringResource(R.string.placeholder_phone_number),
                focusRequester = focusRequester,
                onTextChanged = onPhoneNumberChanged,
                onClearInputClick = onClearPhoneNumberClick
            )
            VerticalSpacer(height = 20.dp)
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(51.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledBackgroundColor = colorResource(CommonRes.color.light_pink),
                    disabledContentColor = colorResource(CommonRes.color.white)
                ),
                enabled = isEnterButtonEnabled,
                onClick = onEnterButtonClick
            ) {
                Text(
                    text = stringResource(R.string.enter_button_text),
                    textAlign = TextAlign.Center,
                    style = AppTypography.buttonText2
                )
            }
        }
        ApplyLoyalProgramTextView()
    }
}

@Composable
private fun BoxScope.ApplyLoyalProgramTextView() {
    val underlinedText = stringResource(R.string.underlined_apply_loyal_program_text)

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .align(Alignment.BottomCenter),
        text = underlinedPartialText(
            text = stringResource(R.string.apply_loyal_program_text, underlinedText),
            underlinedText = underlinedText
        ),
        textAlign = TextAlign.Center,
        style = AppTypography.caption1,
        color = colorResource(id = CommonRes.color.text_gray)
    )
}