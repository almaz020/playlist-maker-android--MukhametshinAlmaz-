package com.almaz.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import com.almaz.playlistmaker.domain.PlaylistsRepository
import com.almaz.playlistmaker.domain.TracksRepository

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long,
    private val tracksRepository: TracksRepository
): ViewModel() {
    val playlist = playlistsRepository.getPlaylist(playlistId)

    val tracks =
        tracksRepository.getTracksForPlaylist(playlistId)
}