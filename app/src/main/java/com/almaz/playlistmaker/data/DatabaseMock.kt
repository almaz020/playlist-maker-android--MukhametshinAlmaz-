package com.almaz.playlistmaker.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DatabaseMock(val scope: CoroutineScope) {

    private val historyList = mutableListOf<String>()

    // SharedFlow для уведомления об изменениях
    private val _historyUpdates = MutableSharedFlow<Unit>(replay = 1)
    val historyFlow: Flow<List<String>> = _historyUpdates
        .map { historyList.toList() } // конвертируем список в snapshot
        .onStart { emit(historyList.toList()) } // отдаем начальное значение

    fun addToHistory(word: String) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }
}