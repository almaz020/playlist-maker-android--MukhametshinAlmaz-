package com.almaz.playlist_maker

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class SettingsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(
            WindowInsets.Type.systemBars() or
                    WindowInsets.Type.navigationBars()
        )
        setContent {
            SettingsActivityScreen()
        }
    }
}

@Composable
fun SettingsActivityScreen(
    modifier: Modifier = Modifier
) {
    Column {
        PanelHeader(
            title = stringResource(R.string.app_settings),
            isButtonEnabled = true
        )
        SettingsMenu(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 24.dp
                )
        )
    }

}

@Composable
fun SettingsMenu(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SettingsMenuRow(
                text = stringResource(R.string.dark_theme),
                painter = painterResource(R.drawable.theme_switcher),
                modifier = Modifier
                    .padding(
                        top = 21.dp,
                        end = 18.dp,
                        bottom = 22.dp,
                    )
            )
            SettingsMenuRow(
                text = stringResource(R.string.share_app),
                painter = painterResource(R.drawable.share),
                modifier = Modifier
                    .padding(
                        top = 22.dp,
                        end = 16.dp,
                        bottom = 21.dp,
                    )
            )
            SettingsMenuRow(
                text = stringResource(R.string.write_to_support),
                painter = painterResource(R.drawable.support),
                modifier = Modifier
                    .padding(
                        top = 22.dp,
                        end = 14.dp,
                        bottom = 21.dp,
                    )
            )
            SettingsMenuRow(
                text = stringResource(R.string.user_agreement),
                painter = painterResource(R.drawable.arrow),
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        end = 20.dp,
                        bottom = 23.dp,
                    )
            )

        }
    }
}

@Composable
fun SettingsMenuRow(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = 21.dp,
                    start = 16.dp,
                    bottom = 21.dp,
                )
                .weight(1f),
            text = text,
            fontSize = 16.sp,
            fontFamily = FontFamily(
                Font(R.font.yandexsanstextregular)
            ),
        )
        Image(
            modifier = modifier,
            painter = painter,
            contentDescription = null,
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewSettingsActivityScreen() {
    SettingsActivityScreen()
}