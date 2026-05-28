package com.almaz.playlistmaker.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaz.playlistmaker.domain.SearchHistoryRepository
import com.almaz.playlistmaker.domain.TracksRepository
import com.almaz.playlistmaker.ui.search.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository : SearchHistoryRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                        searchHistoryRepository.addToHistory(query)
                    }
                }
        }
    }
    fun updateQuery(query: String) {
        _searchQuery.value = query

    }
    fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }

                val list = tracksRepository.searchTracks(request)

                _searchScreenState.update { SearchState.Success(list) }

            } catch (_: IOException) {
                _searchScreenState.update { SearchState.Fail("Нет интернета") }

            } catch (_: Exception) {
                _searchScreenState.update { SearchState.Fail("Ошибка сервера") }
            }
        }
    }
    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }
    fun getHistoryList() = searchHistoryRepository.getHistoryRequests()
}