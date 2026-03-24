package com.almaz.playlistmaker.ui.playlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.almaz.playlistmaker.ui.view_model.PlaylistViewModel

@Composable
fun PlaylistScreen(
    playlistViewModel: PlaylistViewModel,
    index: Int,
    onClick: (Int?) -> Unit
) {
    val playList = playlistViewModel.playlist.collectAsState(null)


}