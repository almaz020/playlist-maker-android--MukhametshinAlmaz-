package com.almaz.playlist_maker

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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