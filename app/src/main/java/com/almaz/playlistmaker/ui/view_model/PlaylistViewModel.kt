package com.almaz.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.almaz.playlistmaker.domain.PlaylistsRepository
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long,
    private val tracksRepository: TracksRepository
): ViewModel() {
    val playlist = playlistsRepository.getPlaylist(playlistId)

    val tracks =
        tracksRepository.getTracksForPlaylist(playlistId)

    fun deleteTrackFromPlaylist(trackId: Long) {
        viewModelScope.launch {
            tracksRepository.deleteTrackFromPlaylist(trackId)
        }

    }


}