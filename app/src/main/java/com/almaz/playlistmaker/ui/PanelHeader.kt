package com.almaz.playlistmaker.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.almaz.playlistmaker.R

@Composable
fun PanelHeader(
    modifier: Modifier = Modifier,
    title: String,
    isButtonEnabled: Boolean = false,
    textColor: Color = colorResource(R.color.black),
    onBack: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (isButtonEnabled) {
            Row(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        start = 4.dp,
                        bottom = 4.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            bottom = 16.dp,
                            end = 28.dp
                        )
                        .clickable(
                            onClick = onBack
                        ),
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                )
                ScreenTitle(
                    title = title
                )
            }
        }
        else {
            ScreenTitle(
                modifier = Modifier
                    .padding(
                        top = 14.dp,
                        start = 16.dp,
                    ),
                title = title,
                textColor = textColor
            )
        }

    }
}
