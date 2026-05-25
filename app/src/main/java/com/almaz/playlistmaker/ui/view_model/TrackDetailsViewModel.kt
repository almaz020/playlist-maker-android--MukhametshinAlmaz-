package com.almaz.playlistmaker.ui.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrackDetailsViewModel(
    private val tracksRepository : TracksRepository
): ViewModel() {

    fun getTrack(track: Track): StateFlow<Track?> =
        tracksRepository.getTrackById(track)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )
    fun updateTrackFavoriteStatus(track: Track) {
        viewModelScope.launch {
            tracksRepository.updateTrackFavoriteStatus(track)
        }
    }

}