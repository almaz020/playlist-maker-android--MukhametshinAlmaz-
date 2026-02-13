package com.almaz.playlistmaker.domain.impl

import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.domain.TrackRepository
import com.almaz.playlistmaker.domain.api.TrackSearchInteractor

class TrackSearchInteractorImpl(private val repository: TrackRepository) : TrackSearchInteractor {

    override suspend fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }

}