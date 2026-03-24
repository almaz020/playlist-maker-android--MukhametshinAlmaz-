package com.almaz.playlistmaker.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.favorites.FavoritesScreen
import com.almaz.playlistmaker.ui.main.MainScreen
import com.almaz.playlistmaker.ui.playlist.AddNewPlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistScreenEnum
import com.almaz.playlistmaker.ui.playlist.PlaylistsScreen
import com.almaz.playlistmaker.ui.search.SearchScreen
import com.almaz.playlistmaker.ui.settings.SettingsScreen
import com.almaz.playlistmaker.ui.view_model.PlaylistsModalBottomViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.SearchViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel

@Composable
fun PlaylistHost(navController: NavHostController) {

    val playlistsViewModel: PlaylistsViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel()
    val trackDetailsViewModel: TrackDetailsViewModel = viewModel()

    val playlistsModalBottomViewModel: PlaylistsModalBottomViewModel = viewModel()

    fun navigateTo(screen: PlaylistScreenEnum) {
        navController.navigate(screen.name) {
            launchSingleTop = true
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

    NavHost(
    navController = navController,
    startDestination = PlaylistScreenEnum.Main.name
    ) {
        composable(PlaylistScreenEnum.Main.name) {
            MainScreen(
                onGoToSearch = { navigateTo(PlaylistScreenEnum.Search) },
                onGoToSettings = { navigateTo(PlaylistScreenEnum.Settings) },
                onGoToPlaylists = { navigateTo(PlaylistScreenEnum.Playlists) },
                onGoToFavorites = { navigateTo(PlaylistScreenEnum.Favorites) },
            )
        }

        composable(PlaylistScreenEnum.Search.name) {
            SearchScreen(
                onBack = { navigateBack() },
                searchViewModel = searchViewModel,
                goToTrackDetailsScreen = { track ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("track", track)

                    navController.navigate(PlaylistScreenEnum.TrackDetailsScreen.name)
                }
            )
        }

        composable(PlaylistScreenEnum.Settings.name) {
            SettingsScreen(
                onBack = { navigateBack() }
            )
        }


        composable(PlaylistScreenEnum.Favorites.name) {
            FavoritesScreen (
                onBack = { navigateBack() },
                trackDetailsViewModel = trackDetailsViewModel
            )
        }
        composable(PlaylistScreenEnum.NewPlaylist.name) {
            AddNewPlaylistScreen (
                onBack = { navigateBack() },
                onCreateClicked = { name, description -> playlistsViewModel.createNewPlayList(name, description) }
            )
        }

        composable(PlaylistScreenEnum.TrackDetailsScreen.name) {
            val track = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Track>("track")

            if (track != null) {
                TrackDetailsScreen(
                    trackSource = track,
                    onBack = { navController.popBackStack() },
                    trackDetailsViewModel = trackDetailsViewModel,
                    playlistsViewModel = playlistsViewModel
                )
            }
        }
    }
}