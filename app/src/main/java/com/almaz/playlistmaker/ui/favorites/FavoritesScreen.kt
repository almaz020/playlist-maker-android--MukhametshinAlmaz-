package com.almaz.playlistmaker.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.modifier.ModifierLocalMap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.ui.TrackListItem
import com.almaz.playlistmaker.ui.view_model.FavoritesScreenViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel

@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    favoritesViewModel: FavoritesScreenViewModel
) {
    val tracks by favoritesViewModel.favoriteTracks.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PanelHeader(
                title = stringResource(R.string.favourite_tracks),
                isButtonEnabled = true,
                onBack = onBack
            )
            if (tracks.isEmpty()) {
                Column(
                    modifier = Modifier.padding(top = 150.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.empty),
                        contentDescription = null
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.empty),
                        color = Color.Black,
                        fontSize = 19.sp,
                        fontFamily = FontFamily(
                            Font(R.font.yandexsanstextmedium)
                        ),
                        textAlign = TextAlign.Center
                    )
                }

            }
            else {
                LazyColumn {
                    items(tracks.size) { index ->
                        TrackListItem(
                            track = tracks[index],
                            onLongClick = { favoritesViewModel.deleteTrackFromFavorites(tracks[index].id) }
                        )
                    }
                }
            }

        }
    }
}