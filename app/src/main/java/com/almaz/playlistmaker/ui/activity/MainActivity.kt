package com.almaz.playlistmaker.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.almaz.playlistmaker.ui.PlaylistHost
import com.almaz.playlistmaker.ui.view_model.SearchViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(
            WindowInsets.Type.systemBars() or
                    WindowInsets.Type.navigationBars()
        )

        setContent {
            val navController = rememberNavController()
            PlaylistHost(navController)
        }
    }
}