package com.almaz.playlistmaker.data.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlin.collections.mutableListOf

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val HISTORY_KEY = stringPreferencesKey("history_key")
    }

    fun getHistory(): Flow<List<String>> {
        return dataStore.data.map { prefs ->

            val json = prefs[HISTORY_KEY] ?: return@map emptyList()

            Gson().fromJson(
                json,
                object : TypeToken<List<String>>() {}.type
            )
        }

    }
    suspend fun addEntry(word: String) {

        dataStore.edit { prefs ->

            val json = prefs[HISTORY_KEY]

            val currentList: MutableList<String> =
                if (json.isNullOrEmpty()) {
                    mutableListOf<String>()
                } else {
                    Gson().fromJson(
                        json,
                        object : TypeToken<List<String>>() {}.type
                    )
                }.toMutableList()

            currentList.remove(word)

            currentList.add(0, word)

            if (currentList.size > 10) {
                currentList.subList(10, currentList.size).clear()
            }

            prefs[HISTORY_KEY] = Gson().toJson(currentList)
        }
    }


}