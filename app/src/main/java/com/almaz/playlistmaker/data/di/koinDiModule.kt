package com.almaz.playlistmaker.data.di

import com.almaz.playlistmaker.data.DatabaseMock
import com.almaz.playlistmaker.data.PlaylistsRepositoryImpl
import com.almaz.playlistmaker.data.SearchHistoryRepositoryImpl
import com.almaz.playlistmaker.data.network.TracksRepositoryImpl
import com.almaz.playlistmaker.domain.PlaylistsRepository
import com.almaz.playlistmaker.domain.SearchHistoryRepository
import com.almaz.playlistmaker.domain.TracksRepository
import com.almaz.playlistmaker.ui.view_model.FavoritesScreenViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsModalBottomViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.SearchViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<TracksRepository> { TracksRepositoryImpl(get()) }
    single<PlaylistsRepository> { PlaylistsRepositoryImpl(get()) }
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }
    viewModel { FavoritesScreenViewModel(get()) }
    viewModel { PlaylistsModalBottomViewModel(get()) }
    viewModel { PlaylistsViewModel(get(), get(), get()) }
    viewModel { (playlistId: Long) ->
        PlaylistViewModel(get(), playlistId)
    }
    viewModel { TrackDetailsViewModel(get()) }
}

val dataModule = module {
    single { DatabaseMock() }
}