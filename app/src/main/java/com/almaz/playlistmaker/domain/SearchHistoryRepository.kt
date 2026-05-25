package com.almaz.playlistmaker.domain

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getHistoryRequests(): Flow<List<String>>

    suspend fun addToHistory(word: String)
}