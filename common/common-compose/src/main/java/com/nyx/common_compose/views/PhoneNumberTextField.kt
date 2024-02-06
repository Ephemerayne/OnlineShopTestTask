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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography
import kotlin.math.min

private object PhoneNumberDefaults {
    const val MASK = "+7 XXX XXX-XX-XX"
    const val INPUT_LENGTH = 10
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    input: String,
    placeholder: String,
    focusRequester: FocusRequester,
    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp
    ),
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
    visualTransformation: VisualTransformation = MaskVisualTransformation(PhoneNumberDefaults.MASK),
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
                if (input.length <= PhoneNumberDefaults.INPUT_LENGTH) {
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


//TODO
private class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != 'X' }

    override fun filter(input: AnnotatedString): TransformedText {
        // Фильтрация ввода, оставляем только цифры
        val digitsOnly = input.text.filter { it.isDigit() }

        // Игнорирование первой цифры, если это "7"
        val effectiveInput = if (digitsOnly.startsWith("7")) digitsOnly.drop(1) else digitsOnly

        var result = ""
        var maskIndex = 0
        var inputIndex = 0

        while (inputIndex < effectiveInput.length && maskIndex < mask.length) {
            if (mask[maskIndex] == 'X') {
                result += effectiveInput[inputIndex]
                inputIndex++
            } else {
                result += mask[maskIndex]
            }
            maskIndex++
        }

        return TransformedText(AnnotatedString(result), offsetTranslator(effectiveInput.length))
    }

    private fun offsetTranslator(inputLength: Int): OffsetMapping {
        return object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = 0
                var xCount = 0
                // Перебираем символы маски и учитываем только те, которые соответствуют введенным символам (X)
                for (i in mask.indices) {
                    if (i >= mask.length || xCount >= inputLength) break // Убедитесь, что мы не выходим за пределы
                    if (mask[i] == 'X') {
                        if (xCount == offset) {
                            break
                        }
                        xCount++
                    }
                    transformedOffset++
                }
                // Возвращаем минимальное значение из рассчитанного смещения и длины всего преобразованного текста
                // Это предотвращает выход за пределы диапазона
                return min(transformedOffset, mask.substring(0, transformedOffset).length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var xCount = 0
                for (i in 0 until min(offset, mask.length)) {
                    if (mask[i] == 'X') {
                        xCount++
                    }
                    if (i == offset - 1) break
                }
                originalOffset = xCount
                return originalOffset
            }
        }
    }
}