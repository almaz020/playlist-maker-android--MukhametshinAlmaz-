package com.almaz.playlistmaker.domain

import com.almaz.playlistmaker.data.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getPlaylist(playlistId: Long): Flow<Playlist?>

    fun getAllPlaylists(): Flow<List<Playlist>>

    suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?)

    suspend fun deletePlaylistById(id: Long)
}