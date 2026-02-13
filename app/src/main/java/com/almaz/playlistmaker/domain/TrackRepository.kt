package com.almaz.playlistmaker.domain

import com.almaz.playlistmaker.data.network.Track

interface TrackRepository {
    suspend fun searchTracks(expression: String): List<Track>

}