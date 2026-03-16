package com.almaz.playlistmaker.data.network

import com.almaz.playlistmaker.data.DatabaseMock
import com.almaz.playlistmaker.data.ITunesApiService
import com.almaz.playlistmaker.data.dto.BaseResponse
import com.almaz.playlistmaker.data.dto.TrackSearchRequest
import com.almaz.playlistmaker.data.dto.TracksSearchResponse
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TracksRepositoryImpl(
    private val scope: CoroutineScope
) : TracksRepository {
    private val database = DatabaseMock(
        scope = scope
    )
    private val ITunesBaseUrl: String = " https://itunes.apple.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(ITunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitNetworkClient = retrofit.create(ITunesApiService::class.java)

    private val retrofitNetworkClientImpl = RetrofitNetworkClient(retrofitNetworkClient)

    override suspend fun searchTracks(expression: String): List<Track> {
        val request = TrackSearchRequest(expression = expression)
        val response: BaseResponse = retrofitNetworkClientImpl.doRequest(dto = request)

        when(response) {
            is TracksSearchResponse -> return response.results.map {
                val timeMillis = it.trackTimeMillis

                val totalSeconds = timeMillis / 1000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60

                val formattedTime = "%d:%02d".format(minutes, seconds)
                Track(
                    id = it.id,
                    trackName = it.trackName,
                    artistName = it.artistName,
                    trackTime = formattedTime,
                    image = it.image,
                    favorite = false,
                    playlistId = 0
                )
            }
            else -> {
                return emptyList()
            }
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun insertTrackToPlaylist(track: Track?, playlistId: Long) {
        database.insertTrack(track?.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }
}