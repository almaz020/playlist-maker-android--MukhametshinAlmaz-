package com.almaz.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import com.almaz.playlistmaker.domain.PlaylistsRepository

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
): ViewModel() {
    val playlist = playlistsRepository.getPlaylist(playlistId)


}