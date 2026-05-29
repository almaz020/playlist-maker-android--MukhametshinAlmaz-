package com.almaz.playlistmaker.domain

import com.almaz.playlistmaker.data.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track)

    suspend fun updateTrackFavoriteStatus(track: Track)
    suspend fun deleteTracksByPlaylistId(id: Long)

    fun getTrackById(track: Track): Flow<Track?>

    fun getTracksForPlaylist(playlistId: Long): Flow<List<Track>>

    fun getTracksCount(playlistId: Long): Flow<Int>

    suspend fun deleteTrackById(id: Long)

    suspend fun deleteTrackFromPlaylist(trackId: Long)

}