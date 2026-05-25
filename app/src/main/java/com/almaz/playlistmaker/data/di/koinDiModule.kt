package com.almaz.playlistmaker.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.almaz.playlistmaker.data.ITunesApiService
import com.almaz.playlistmaker.data.NetworkClient
import com.almaz.playlistmaker.data.PlaylistsRepositoryImpl
import com.almaz.playlistmaker.data.SearchHistoryRepositoryImpl
import com.almaz.playlistmaker.data.database.AppDatabase
import com.almaz.playlistmaker.data.datastore.dataStore
import com.almaz.playlistmaker.data.datastore.preferences.SearchHistoryPreferences
import com.almaz.playlistmaker.data.network.RetrofitNetworkClient
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
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single<TracksRepository> { TracksRepositoryImpl(get(), get()) }
    single<PlaylistsRepository> { PlaylistsRepositoryImpl(get()) }
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }
    viewModel { FavoritesScreenViewModel(get()) }
    viewModel { PlaylistsModalBottomViewModel(get()) }
    viewModel { PlaylistsViewModel(get(), get()) }
    viewModel { (playlistId: Long) ->
        PlaylistViewModel(get(), playlistId, get())
    }
    viewModel { TrackDetailsViewModel(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "playlists_maker"
        ).build()
    }
}
val datastoreModule = module {
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    single {
        SearchHistoryPreferences(get())
    }

}


val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ITunesApiService> {
        get<Retrofit>().create(ITunesApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }
}