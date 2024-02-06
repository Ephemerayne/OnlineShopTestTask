package com.nyx.common_compose.views

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nyx.common_compose.R
import com.nyx.common_compose.typography.AppTypography

@Composable
fun HideOrShowView(
    isViewVisible: Boolean,
    onHideOrShowClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .offset((-12).dp)
            .noRippleClickable(onClick = onHideOrShowClick)
            .padding(12.dp),
        text = stringResource(if (isViewVisible) R.string.hide_text else R.string.more_details_text),
        color = colorResource(id = R.color.text_gray),
        textAlign = TextAlign.Center,
        style = AppTypography.buttonText1
    )
}

@Composable
fun ExpandOrReduceView(
    isViewExpanded: Boolean,
    onHideOrShowClick: (() -> Unit)? = null,
) {
    onHideOrShowClick?.let {
        Text(
            modifier = Modifier
                .offset((-12).dp)
                .noRippleClickable(onClick = onHideOrShowClick)
                .padding(12.dp),
            text = stringResource(if (isViewExpanded) R.string.hide_text else R.string.more_details_text),
            color = colorResource(id = R.color.text_gray),
            textAlign = TextAlign.Center,
            style = AppTypography.buttonText1
        )
    }
}