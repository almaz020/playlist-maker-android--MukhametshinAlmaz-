package com.almaz.playlistmaker.ui.playlist

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel

@Composable
fun PlaylistsModalBottomScreen(
    playlistsViewModel : PlaylistsViewModel,
    track: Track?,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
            PanelHeader(
                title = stringResource(R.string.playlists),
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

                        PlaylistListItem(playlist = playlists[index], tracksCount) {
                            playlistsViewModel.insertTrackToPlaylist(track!!, playlists[index].id)
                            Toast.makeText(context, "Трек успешно добавлен в плейлист", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        }
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }
    }
}