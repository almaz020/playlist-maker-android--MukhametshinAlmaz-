package com.almaz.playlistmaker.domain

import com.almaz.playlistmaker.data.network.Track

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}