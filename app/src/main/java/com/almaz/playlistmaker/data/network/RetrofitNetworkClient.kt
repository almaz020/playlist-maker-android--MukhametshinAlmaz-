package com.almaz.playlistmaker.data.network

import com.almaz.playlistmaker.data.ITunesApiService
import com.almaz.playlistmaker.data.NetworkClient
import com.almaz.playlistmaker.data.dto.BaseResponse
import com.almaz.playlistmaker.data.dto.TrackSearchRequest
import java.io.IOException

class RetrofitNetworkClient(private val api: ITunesApiService) : NetworkClient {

    override suspend fun doRequest(dto: Any): BaseResponse {
        return try {
            when (dto) {
                is TrackSearchRequest -> api.searchTracks(
                    query = dto.expression,
                    media = "music",
                    entity = "song",
                    limit = 10
                )

                else -> BaseResponse().apply {
                    resultCode = 400
                    errorMessage = "Invalid request type: expected TracksSearchRequest or String"
                }
            }
        } catch (e: IOException) {
            BaseResponse().apply {
                resultCode = -1
                errorMessage = "Network error: ${e.message ?: "Unknown IO error"}"
            }
        } catch (e: Exception) {
            BaseResponse().apply {
                resultCode = -2
                errorMessage = "Unexpected error: ${e.message ?: "Unknown error"}"
            }
        }
    }
}