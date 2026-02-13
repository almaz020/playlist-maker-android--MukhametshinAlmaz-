package com.almaz.playlistmaker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? =  {
        Icon(
            painter = painterResource(R.drawable.search_gray),
            contentDescription = null,
            tint = colorResource(R.color.light_gray_for_search_field))
    },
    trailingIcon: (@Composable () -> Unit)? = {
        Icon(
            painter = painterResource(R.drawable.clear_search_field),
            contentDescription = null,
            tint = colorResource(R.color.light_gray_for_search_field))
    },
    placeholderText: String = "Поиск",
    fontSize: TextUnit = 16.sp,
    viewModel: SearchViewModel,
) {
    var text by rememberSaveable { mutableStateOf("") }

    BasicTextField(
        modifier = modifier
            .padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = colorResource(R.color.light_gray),
            )
            .fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            viewModel.search(text)

        },
        singleLine = true,
        cursorBrush = SolidColor(colorResource(R.color.blue_for_search_cursor)),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(R.font.yandexsanstextregular))
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier
                            .padding(
                                top = 11.dp,
                                start = 13.dp,
                                bottom = 11.dp,
                                end = 9.dp,
                            )
                    ) {
                        leadingIcon()
                    }
                }
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            text = placeholderText,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize,
                                fontFamily = FontFamily(
                                    Font(R.font.yandexsanstextregular))
                            )
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null && !text.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                                end = 14.dp,
                                bottom = 12.dp
                            )
                            .clickable(
                                onClick = { text = "" }
                            )
                    ) {
                        trailingIcon()
                    }
                }
            }
        }
    )
}

