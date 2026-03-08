package com.almaz.playlistmaker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.PlaylistBottomSheet

import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.view_model.PlaylistsModalBottomViewModel
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel

@Composable
fun TrackDetailsScreen(
    onBack: () -> Unit = {},
    trackSource: Track,
    trackDetailsViewModel: TrackDetailsViewModel,
    playlistsViewModel: PlaylistsViewModel,
) {
    val track by trackDetailsViewModel.getTrack(trackSource).collectAsState()
    var isShowSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PanelHeader(
            title = "",
            isButtonEnabled = true,
            onBack = onBack,
        )
        Image(
            modifier = Modifier.fillMaxWidth().height(312.dp).padding(end = 24.dp, start = 24.dp, top = 26.dp),
            painter = painterResource(R.drawable.ic_music),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(end = 24.dp, start = 24.dp, top = 24.dp),
            text = track?.trackName ?: "",
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
            textAlign = TextAlign.Start,
        )
        Text(
            modifier = Modifier.padding(end = 24.dp, start = 24.dp, top = 12.dp),
            text = track?.trackName ?: "",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
            textAlign = TextAlign.Start,
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(end = 24.dp, start = 24.dp, top = 54.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButton(
                modifier = Modifier,
                containerColor = Color.Transparent,
                contentColor = Color.Unspecified,
                shape = CircleShape,
                onClick =  { isShowSheet = true  },
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            )
            {
                Icon(
                    painter = painterResource(R.drawable.new_playlist),
                    contentDescription = null,
                )
            }
            FloatingActionButton(
                containerColor = Color.Transparent,
                contentColor = Color.Unspecified,
                shape = CircleShape,
                modifier = Modifier,
                onClick =  { track?.let {
                    trackDetailsViewModel.updateTrackFavoriteStatus(it, !it.favorite)
                } },
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            )
            {
                Icon(
                    painter = painterResource(R.drawable.add_favorite),
                    contentDescription = null,
                    tint = if (track?.favorite ?: false) Color.Red else Color.Unspecified
                )
            }
            PlaylistBottomSheet(
                isShowPanel = isShowSheet,
                onDismissRequest = { isShowSheet = false },
                playlistsViewModel = playlistsViewModel,
                track = track
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 9.dp, bottom = 8.dp),
                text = stringResource(R.string.trackTime),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
                color = colorResource(R.color.light_gray_for_search_field)
            )
            Text(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp, end = 16.dp),
                text = track?.trackTime ?: "",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
            )
        }
    }
}