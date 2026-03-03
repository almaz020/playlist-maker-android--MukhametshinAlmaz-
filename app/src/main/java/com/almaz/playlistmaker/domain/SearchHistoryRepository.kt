package com.almaz.playlistmaker.domain

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): Flow<List<String>>

    fun addToHistory(word: String)
}