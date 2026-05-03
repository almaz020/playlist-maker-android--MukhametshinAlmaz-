package com.almaz.playlistmaker.data

import com.almaz.playlistmaker.data.database.AppDatabase
import com.almaz.playlistmaker.data.database.toPlaylist
import com.almaz.playlistmaker.domain.PlaylistsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase
) : PlaylistsRepository {

    private val dao = database.PlaylistsDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return dao.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return dao.getAllPlaylists().map { playlists -> playlists.map { it.toPlaylist() } }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        dao.addNewPlaylist(
            name = name,
            description = description
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        dao.deletePlaylistById(playlistId = id)
    }
}