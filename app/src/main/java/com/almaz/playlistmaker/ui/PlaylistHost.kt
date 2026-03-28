package com.almaz.playlistmaker.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.almaz.playlistmaker.data.DatabaseMock
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.favorites.FavoritesScreen
import com.almaz.playlistmaker.ui.main.MainScreen
import com.almaz.playlistmaker.ui.playlist.AddNewPlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistScreenEnum
import com.almaz.playlistmaker.ui.playlist.PlaylistsScreen
import com.almaz.playlistmaker.ui.search.SearchScreen
import com.almaz.playlistmaker.ui.settings.SettingsScreen
import com.almaz.playlistmaker.ui.view_model.PlaylistViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsModalBottomViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.SearchViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlaylistHost(navController: NavHostController) {

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
                searchViewModel = koinViewModel(),
                goToTrackDetailsScreen = { track ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("track", track)

                    navController.navigate(PlaylistScreenEnum.TrackDetailsScreen.name)
                }
            )
        }
        composable(PlaylistScreenEnum.Playlists.name) {
            PlaylistsScreen(
                addNewPlaylist = { navController.navigate(PlaylistScreenEnum.NewPlaylist.name) },
                //navigateToPlaylist = { index -> navController.navigate("${Destination.PLAYLIST_SCREEN.name}/$index") },
                onBack = { navController.popBackStack() },
                playlistsViewModel = koinViewModel()
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
                trackDetailsViewModel = koinViewModel()
            )
        }
        composable(PlaylistScreenEnum.NewPlaylist.name) {
            val playlistsViewModel: PlaylistsViewModel = koinViewModel()
            AddNewPlaylistScreen(
                onBack = { navigateBack() },
                onCreateClicked = { name, description ->
                    playlistsViewModel.createNewPlayList(name, description)
                }
            )
        }

        composable(PlaylistScreenEnum.TrackDetailsScreen.name) {
            val track = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Track>("track")

            if (track != null) {
                val trackDetailsViewModel: TrackDetailsViewModel = koinViewModel()
                val playlistsViewModel: PlaylistsViewModel = koinViewModel()
                TrackDetailsScreen(
                    trackSource = track,
                    onBack = { navController.popBackStack() },
                    trackDetailsViewModel = trackDetailsViewModel,
                    playlistsViewModel = playlistsViewModel
                )
            }
        }

//        composable(
//            route = "${Destination.PLAYLIST_SCREEN.name}/{index}",
//            arguments = listOf(
//                navArgument("index") {
//                    type = NavType.IntType
//                }
//            )
//        ) { backStackEntry ->
//            val index = backStackEntry.arguments?.getInt("index") ?: 0
//            PlaylistScreen(
//                modifier = modifier,
//                viewModel = koinViewModel { parametersOf(index.toLong()) },
//                navigateToTrack = { navController.navigate(it) },
//                navigateBack = { navController.popBackStack() }
//            )
//        }
    }
}