package com.almaz.playlistmaker.data

import com.almaz.playlistmaker.data.network.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DatabaseMock {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()
    private val tracks = mutableListOf<Track>()

    private val _tracksUpdates = MutableSharedFlow<Unit>(replay = 1)

    fun getHistory(): List<String> {
        return historyList.toList()
    }
    val historyFlow: Flow<List<String>> = _historyUpdates
        .map { historyList.toList() }
        .onStart { emit(historyList.toList()) }

    fun addToHistory(word: String) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }
    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500) // Имитируем задержку загрузки из базы данных
        val filteredPlaylists = mutableListOf<Playlist>()
        playlists.forEach { playlist ->
            val playlistTracks = tracks.filter { track ->
                track.playlistId == playlist.id
            }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }

        emit(filteredPlaylists.toList())
        delay(100)
    }
    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == id })
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
    }
    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.id == trackId }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> =
        _tracksUpdates
            .onStart { emit(Unit) }
            .map {
                tracks.find { it.trackName == track.trackName && it.artistName == track.artistName }
            }

    fun insertTrack(track: Track?) {
        tracks.removeIf { it.id == track?.id }
        if (track != null) {
            tracks.add(track)
        }

        scope.launch {
            _tracksUpdates.emit(Unit)
        }
    }

    fun getFavoriteTracks(): Flow<List<Track>> =
        _tracksUpdates
            .onStart { emit(Unit) }
            .map {
                tracks.filter { it.favorite }
            }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
    }

    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { it.trackName.contains(expression, true) }
    }
}