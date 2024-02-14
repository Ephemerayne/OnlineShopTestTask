package com.nyx.common_compose.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_impl.utils.PhoneNumberDefaults

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    input: String,
    placeholder: String,
    focusRequester: FocusRequester,
    textStyle: TextStyle = AppTypography.placeholderText,
    cursorColor: Color = Color.Black,
    fieldColor: Color = colorResource(id = R.color.background_light_gray),
    isEnabled: Boolean = true,
    hasClearIcon: Boolean = true,
    contentPaddings: PaddingValues = PaddingValues(
        start = 16.dp,
        end = 34.dp,
        top = 16.dp,
        bottom = 16.dp
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    ),
    visualTransformation: VisualTransformation = PhoneVisualTransformation(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onTextChanged: (String) -> Unit,
    onClearInputClick: () -> Unit,
) {

    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = input,
            onValueChange = { input ->
                val availableInputLength = if (input.isNotEmpty() && input.startsWith("7")) {
                    PhoneNumberDefaults.INPUT_LENGTH + 1
                } else {
                    PhoneNumberDefaults.INPUT_LENGTH
                }

                if (input.length <= availableInputLength) {
                    onTextChanged(input)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(fieldColor)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused },
            textStyle = textStyle,
            cursorBrush = SolidColor(cursorColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = "",
                placeholder = {
                    if (input.isEmpty()) {
                        Text(
                            text = if (isFocused) PhoneNumberDefaults.MASK else placeholder,
                            color = colorResource(id = R.color.text_gray),
                            style = AppTypography.placeholderText
                        )
                    }
                },
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                enabled = isEnabled,
                singleLine = true,
                interactionSource = interactionSource,
                contentPadding = contentPaddings,
            )
        }
        if (input.isNotBlank() && isFocused && hasClearIcon) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .clickable(onClick = onClearInputClick),
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    }
}

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        if (text.isEmpty()) {
            return TransformedText(AnnotatedString(""), getOffsetMapping(""))
        }

        val trimmed = if (text.text.startsWith("7")) {
            text.text.substring(1)
        } else {
            text.text
        }

        val digitsOnly = trimmed.filter { it.isDigit() }

        var phoneNumber = "+7 "

        digitsOnly.forEachIndexed { index, c ->
            when (index) {
                in 0..2 -> phoneNumber += if (index == 2) "$c " else c
                in 3..5 -> phoneNumber += if (index == 5) "$c-" else c
                in 6..7 -> phoneNumber += if (index == 7) "$c-" else c
                else -> phoneNumber += c
            }
        }

        val visualText = TextFieldValue(phoneNumber, TextRange(phoneNumber.length))

        return TransformedText(AnnotatedString(visualText.text), getOffsetMapping(visualText.text))
    }

    private fun getOffsetMapping(text: String): OffsetMapping {
        return object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return text.length.coerceAtMost(offset + 999)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return (offset - 999).coerceAtLeast(0)
            }
        }
    }
}