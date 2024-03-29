package com.nyx.common_compose.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography
import com.nyx.common_impl.utils.isCyrillicInput

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CyrillicTextField(
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
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onTextChanged: (String) -> Unit,
    onClearInputClick: () -> Unit,
) {

    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }


    Box(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            value = input,
            onValueChange = {
                onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(fieldColor)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    border = if (isCyrillicInput(input)) BorderStroke(
                        width = 0.dp,
                        color = Color.Transparent
                    ) else
                        BorderStroke(width = 1.dp, color = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ),
            textStyle = textStyle,
            cursorBrush = SolidColor(cursorColor),
            maxLines = 1,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        ) { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = "",
                placeholder = {
                    if (input.isEmpty()) {
                        Text(
                            text = placeholder,
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
        if (input.isNotEmpty() && isFocused && hasClearIcon) {
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