package com.almaz.playlistmaker.data.network

import com.almaz.playlistmaker.creator.Storage
import com.almaz.playlistmaker.domain.NetworkClient
import com.almaz.playlistmaker.data.dto.BaseResponse
import com.almaz.playlistmaker.data.dto.TrackSearchRequest
import com.almaz.playlistmaker.data.dto.TrackSearchResponse

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(request: Any): TrackSearchResponse {
        val searchList = storage.search((request as TrackSearchRequest).expression)
        return TrackSearchResponse(searchList).apply { resultCode = 200 }
    }
}