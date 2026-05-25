package com.almaz.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.domain.PlaylistsRepository
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.flow.Flow

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long,
): ViewModel() {
    val playlist = playlistsRepository.getPlaylist(playlistId)

}