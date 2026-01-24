package com.almaz.playlist_maker

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


    @Composable
    fun MainScreen(
        modifier: Modifier = Modifier,
        onGoToSearch: () -> Unit = {},
        onGoToSettings: () -> Unit = {}
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(R.color.blue))
        ) {
            PanelHeader(
                modifier = Modifier
                    .padding(
                        bottom = 14.dp,
                    ),
                title = stringResource(R.string.app_name),
                textColor = colorResource(R.color.white)
            )
            MainMenu(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RoundedCornerShape(
                            topEnd = 16.dp,
                            topStart = 16.dp,
                        )
                    )
                    .background(colorResource(R.color.white)),
                onGoToSearch = onGoToSearch,
                onGoToSettings = onGoToSettings
            )
        }
    }


    @Composable
    fun MainMenu(
        modifier: Modifier = Modifier,
        onGoToSearch: () -> Unit = {},
        onGoToSettings: () -> Unit = {}
    ) {
        val context = LocalContext.current

        Box(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth(),
            ) {
                MenuRow(
                    painter = painterResource(R.drawable.search),
                    text = stringResource(R.string.search),
                    onClick = onGoToSearch
                )
                MenuRow(
                    painter = painterResource(R.drawable.playlists),
                    text = stringResource(R.string.playlists),
                    onClick = { Toast.makeText(context, "Нажата кнопка Плейлисты", Toast.LENGTH_LONG).show() }
                )
                MenuRow(
                    painter = painterResource(R.drawable.favourite),
                    text = stringResource(R.string.favourite),
                    onClick = { Toast.makeText(context, "Нажата кнопка Избранное", Toast.LENGTH_LONG).show() }
                )
                MenuRow(
                    painter = painterResource(R.drawable.settings),
                    text = stringResource(R.string.app_settings),
                    onClick = onGoToSettings
                )
            }
        }

    }

    @Composable
    fun MenuRow(
        modifier: Modifier = Modifier,
        painter: Painter,
        text: String,
        onClick: () -> Unit,
    ) {
        Row(
            modifier = modifier
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        start = 12.dp,
                        bottom = 20.dp
                    )
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(
                            end = 10.dp,
                        ),
                    painter = painter,
                    contentDescription = null,
                )
                Text(
                    text = text,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(
                        Font(R.font.yandexsanstextmedium)
                    ),
                )
            }
            Image(
                modifier = Modifier
                    .padding(
                        top = 26.dp,
                        end = 20.dp,
                        bottom = 26.dp
                    ),
                painter = painterResource(R.drawable.arrow),
                contentDescription = null
            )
        }
    }
@Preview(showSystemUi = true)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}