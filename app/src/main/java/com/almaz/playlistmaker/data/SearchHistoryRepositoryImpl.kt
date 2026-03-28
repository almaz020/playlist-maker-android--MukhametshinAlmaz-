package com.almaz.playlistmaker.data

import com.almaz.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val database : DatabaseMock
) : SearchHistoryRepository {



    // Реализация getHistoryRequests через Flow
    override suspend fun getHistoryRequests(): Flow<List<String>> = database.historyFlow

    override fun addToHistory(word: String) {
        database.addToHistory(word)
    }
}