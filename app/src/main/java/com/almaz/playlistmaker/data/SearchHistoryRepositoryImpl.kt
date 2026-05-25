package com.almaz.playlistmaker.data

import com.almaz.playlistmaker.data.datastore.preferences.SearchHistoryPreferences
import com.almaz.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override fun getHistoryRequests(): Flow<List<String>> = preferences.getHistory()

    override suspend fun addToHistory(word: String) {
        preferences.addEntry(word)
    }
}