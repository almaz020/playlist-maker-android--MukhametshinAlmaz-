package com.almaz.playlistmaker.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.almaz.playlistmaker.ui.main.MainScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistScreen
import com.almaz.playlistmaker.ui.search.SearchScreen
import com.almaz.playlistmaker.ui.settings.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {

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
                onGoToSettings = { navigateTo(PlaylistScreen.Settings) }
            )
        }

        composable(PlaylistScreen.Search.name) {
            SearchScreen(
                onBack = { navigateBack() },
            )
        }

        composable(PlaylistScreen.Settings.name) {
            SettingsScreen(
                onBack = { navigateBack() }
            )
        }
    }
}