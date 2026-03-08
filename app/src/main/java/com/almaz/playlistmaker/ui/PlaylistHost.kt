package com.almaz.playlistmaker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.favorites.FavoritesScreen
import com.almaz.playlistmaker.ui.main.MainScreen
import com.almaz.playlistmaker.ui.playlist.AddNewPlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistsScreen
import com.almaz.playlistmaker.ui.search.SearchScreen
import com.almaz.playlistmaker.ui.settings.SettingsScreen
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.SearchViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel

@Composable
fun PlaylistHost(navController: NavHostController) {

    val playlistsViewModel: PlaylistsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val trackDetailsViewModel: TrackDetailsViewModel = viewModel()

    fun navigateTo(screen: PlaylistScreen) {
        navController.navigate(screen.name) {
            launchSingleTop = true
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    NavHost(
    navController = navController,
    startDestination = PlaylistScreen.Main.name
    ) {
        composable(PlaylistScreen.Main.name) {
            MainScreen(
                onGoToSearch = { navigateTo(PlaylistScreen.Search) },
                onGoToSettings = { navigateTo(PlaylistScreen.Settings) },
                onGoToPlaylists = { navigateTo(PlaylistScreen.Playlists) },
                onGoToFavorites = { navigateTo(PlaylistScreen.Favorites) },
            )
        }

        composable(PlaylistScreen.Search.name) {
            SearchScreen(
                onBack = { navigateBack() },
                searchViewModel = searchViewModel,
                goToTrackDetailsScreen = { track ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("track", track)

                    navController.navigate(PlaylistScreen.TrackDetailsScreen.name)
                }
            )
        }

        composable(PlaylistScreen.Settings.name) {
            SettingsScreen(
                onBack = { navigateBack() }
            )
        }

        composable(PlaylistScreen.Playlists.name) {
            PlaylistsScreen (
                onBack = { navigateBack() },
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = { navigateTo(PlaylistScreen.NewPlaylist) },
            )
        }

        composable(PlaylistScreen.Favorites.name) {
            FavoritesScreen (
                onBack = { navigateBack() }
            )
        }
        composable(PlaylistScreen.NewPlaylist.name) {
            AddNewPlaylistScreen (
                onBack = { navigateBack() },
                onCreateClicked = { name, description -> playlistsViewModel.createNewPlayList(name, description) }
            )
        }

        composable(PlaylistScreen.TrackDetailsScreen.name) {
            val track = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Track>("track")

            if (track != null) {
                TrackDetailsScreen(trackSource = track, onBack = { navController.popBackStack() }, trackDetailsViewModel = trackDetailsViewModel)
            }
        }
    }
}