package com.almaz.playlistmaker.data

import com.almaz.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}