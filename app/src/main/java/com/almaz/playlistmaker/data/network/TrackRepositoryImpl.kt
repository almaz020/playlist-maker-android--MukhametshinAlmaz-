package com.almaz.playlistmaker.data.network

import com.almaz.playlistmaker.data.NetworkClient
import com.almaz.playlistmaker.data.database.AppDatabase
import com.almaz.playlistmaker.data.database.toEntity
import com.almaz.playlistmaker.data.database.toTrack
import com.almaz.playlistmaker.data.dto.BaseResponse
import com.almaz.playlistmaker.data.dto.TrackSearchRequest
import com.almaz.playlistmaker.data.dto.TracksSearchResponse
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import java.io.IOException
import kotlin.collections.map

class TracksRepositoryImpl(
    private val retrofitNetworkClientImpl: NetworkClient,
    database: AppDatabase
) : TracksRepository {

    private val dao = database.TracksDao()

    override suspend fun searchTracks(expression: String): List<Track> {
        val request = TrackSearchRequest(expression = expression)
        val response: BaseResponse = retrofitNetworkClientImpl.doRequest(dto = request)


        when(response.resultCode) {
            200 -> {
                response as TracksSearchResponse
                return response.results.map {
                    val timeMillis = it.trackTimeMillis

                    val totalSeconds = timeMillis / 1000
                    val minutes = totalSeconds / 60
                    val seconds = totalSeconds % 60

                    val formattedTime = "%d:%02d".format(minutes, seconds)
                    Track(
                        id = it.trackId,
                        trackName = it.trackName,
                        artistName = it.artistName,
                        trackTime = formattedTime,
                        image = it.artworkUrl100,
                        favorite = false,
                        playlistId = 0
                    )
                }
            }
            -1 -> throw IOException("Нет интернета")

            -2 -> throw RuntimeException("Ошибка сервера")

            else -> throw RuntimeException(response.errorMessage)
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        dao.insertTrack(track.copy(playlistId = playlistId).toEntity())
    }


    override suspend fun deleteTrackFromPlaylist(track: Track) {
        dao.insertTrack(track.copy(playlistId = 0).toEntity())
    }

    override suspend fun updateTrackFavoriteStatus(track: Track) {

        val currentTrack = dao.getTrackOnce(track.id)

        if (currentTrack == null) {

            dao.insertTrack(
                track.copy(favorite = true).toEntity()
            )

        } else {

            dao.updateFavorite(
                id = track.id,
                isFavorite = !currentTrack.favorite
            )
        }
    }

    override suspend fun deleteTracksByPlaylistId(id: Long) {
        dao.deleteTracksByPlaylistId(id)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getFavoriteTracks().map { tracks -> tracks.map { it.toTrack() } }
    }

    override fun getTrackById(track: Track): Flow<Track?> {
        return dao.getTrackById(track.id)
            .map { it?.toTrack() }
    }

    override fun getTracksForPlaylist(
        playlistId: Long
    ): Flow<List<Track>> {

        return dao.getTracksForPlaylist(playlistId)
            .map { list ->
                list.map { it.toTrack() }
            }
    }

    override fun getTracksCount(playlistId: Long): Flow<Int> {
        return dao.getTracksCount(playlistId)
    }

}