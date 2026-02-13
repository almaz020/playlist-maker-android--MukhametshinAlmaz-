package com.almaz.playlistmaker.domain.api

import com.almaz.playlistmaker.data.network.Track

interface TrackSearchInteractor {
    suspend fun searchTracks(expression: String): List<Track>

}