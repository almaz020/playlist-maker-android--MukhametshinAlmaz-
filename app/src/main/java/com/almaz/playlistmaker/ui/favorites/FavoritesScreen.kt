package com.almaz.playlistmaker.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
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
                title = stringResource(R.string.favourite),
                isButtonEnabled = true,
                onBack = onBack
            )
            LazyColumn {
                items(tracks.size) { index ->
                    TrackListItem(
                        track = tracks[index],
                        onClick = { }
                    )
                }
            }
        }
    }
}