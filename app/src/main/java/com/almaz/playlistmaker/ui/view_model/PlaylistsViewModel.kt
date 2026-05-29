package com.almaz.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaz.playlistmaker.data.Playlist
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.domain.PlaylistsRepository
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository
) : ViewModel() {

//    val playlists: Flow<List<Playlist>> = flow {
//        val collectedPlaylists = mutableListOf<Playlist>()
//        playlistsRepository.getAllPlaylists().collect { playlist ->
//            collectedPlaylists.addAll(playlist)
//            emit(collectedPlaylists.toList())
//        }
//    }

    val playlists: StateFlow<List<Playlist>> =
        playlistsRepository.getAllPlaylists()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
        )

    fun getTracksCount(playlistId: Long): Flow<Int> {
        return tracksRepository.getTracksCount(playlistId)
    }
    fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch {
            tracksRepository.insertTrackToPlaylist(track, playlistId)
        }
    }
}