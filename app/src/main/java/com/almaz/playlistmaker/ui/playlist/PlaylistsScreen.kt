package com.almaz.playlistmaker.ui.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.Playlist
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel

@Composable
fun PlaylistsScreen(
    addNewPlaylist: () -> Unit = {},
    navigateToPlaylist: (Playlist) -> Unit = {},
    onBack: () -> Unit,
    playlistsViewModel: PlaylistsViewModel,
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PanelHeader(
                title = stringResource(R.string.playlists),
                isButtonEnabled = true,
                onBack = onBack
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp),
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(playlists.size) { index ->

                        val playlist = playlists[index]

                        val tracksCount by playlistsViewModel
                            .getTracksCount(playlist.id)
                            .collectAsState(initial = 0)

                        PlaylistListItem(playlist = playlists[index],tracksCount ) {
                            navigateToPlaylist(playlists[index])
                        }
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd),
            onClick = { addNewPlaylist() },
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.floating_button),
                contentDescription = null,
            )
        }
    }
}