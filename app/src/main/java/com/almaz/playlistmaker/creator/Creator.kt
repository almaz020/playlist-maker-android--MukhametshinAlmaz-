package com.almaz.playlistmaker.creator

import com.almaz.playlistmaker.data.network.RetrofitNetworkClient
import com.almaz.playlistmaker.data.network.TracksRepositoryImpl
import com.almaz.playlistmaker.domain.TrackRepository

object Creator {
    fun getTracksRepository(): TrackRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}