package com.almaz.playlistmaker.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.R

@Composable
fun ScreenTitle(
    modifier: Modifier = Modifier,
    title: String,
    textColor: Color = colorResource(R.color.black)
) {
    Text(
        modifier = modifier,
        text = title,
        fontSize = 22.sp,
        fontFamily = FontFamily(
            Font(R.font.yandexsanstextmedium)
        ),
        style = TextStyle(
            color = textColor
        )
    )
}