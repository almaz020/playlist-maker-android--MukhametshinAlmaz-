package com.almaz.playlistmaker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.playlist.PlaylistsModalBottomScreen
import com.almaz.playlistmaker.ui.playlist.PlaylistsScreen
import com.almaz.playlistmaker.ui.view_model.PlaylistsModalBottomViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistBottomSheet(
    isShowPanel: Boolean,
    onDismissRequest: () -> Unit,
    playlistsViewModel: PlaylistsViewModel,
    track: Track?
) {
    val sheetState = rememberModalBottomSheetState()

    if (isShowPanel) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            PlaylistsModalBottomScreen(playlistsViewModel =  playlistsViewModel, track, onDismissRequest)
        }
    }
}