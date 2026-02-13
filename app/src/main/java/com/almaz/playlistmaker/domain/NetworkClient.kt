package com.almaz.playlistmaker.domain

import com.almaz.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}