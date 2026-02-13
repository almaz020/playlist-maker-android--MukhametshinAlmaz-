package com.almaz.playlistmaker.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.ui.TrackListItem
import com.almaz.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onGoToSettings: () -> Unit = {},
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
) {

    val screenState by viewModel.searchScreenState.collectAsState()
    Column {
        PanelHeader(
            title = stringResource(R.string.search),
            isButtonEnabled = true,
            onBack = onBack
        )

        SearchField(viewModel = viewModel)

        when (screenState) {
            is SearchState.Initial -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Введите строку для поиска")
                }
            }

            is SearchState.Searching -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(tracks.size) { index ->
                        TrackListItem(track = tracks[index])
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: $error", color = Color.Red)
                }
            }
        }
    }
}