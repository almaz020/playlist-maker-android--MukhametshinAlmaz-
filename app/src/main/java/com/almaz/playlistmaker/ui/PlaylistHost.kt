package com.almaz.playlistmaker.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.almaz.playlistmaker.ui.favorites.FavoritesScreen
import com.almaz.playlistmaker.ui.main.MainScreen
import com.almaz.playlistmaker.ui.playlist.AddNewPlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistsScreen
import com.almaz.playlistmaker.ui.search.SearchScreen
import com.almaz.playlistmaker.ui.settings.SettingsScreen
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.SearchViewModel

@Composable
fun PlaylistHost(navController: NavHostController) {

    val playlistsViewModel: PlaylistsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()

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
                searchViewModel = searchViewModel
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
    }
}