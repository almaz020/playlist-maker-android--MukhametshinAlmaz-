package com.almaz.playlist_maker


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PanelHeader(
    modifier: Modifier = Modifier,
    title: String,
    isButtonEnabled: Boolean = false,
    textColor: Color = colorResource(R.color.black)
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
