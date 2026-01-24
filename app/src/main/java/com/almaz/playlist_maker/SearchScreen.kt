package com.almaz.playlist_maker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onGoToSettings: () -> Unit = {}
) {
    Column {
        PanelHeader(
            title = stringResource(R.string.search),
            isButtonEnabled = true,
            onBack = onBack
        )
        SearchField()
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewSearchActivityScreen() {
    SearchScreen()
}