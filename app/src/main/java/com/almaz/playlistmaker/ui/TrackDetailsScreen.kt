package com.almaz.playlistmaker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.almaz.playlistmaker.PlaylistBottomSheet
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.view_model.PlaylistsViewModel
import com.almaz.playlistmaker.ui.view_model.TrackDetailsViewModel

@Composable
fun TrackDetailsScreen(
    onBack: () -> Unit = {},
    trackSource: Track,
    trackDetailsViewModel: TrackDetailsViewModel,
    playlistsViewModel: PlaylistsViewModel,
) {

    var refreshTrigger by remember { mutableIntStateOf(0) }

    val trackFlow = remember(trackSource, refreshTrigger) {
        trackDetailsViewModel.getTrack(trackSource)
    }

    val currentTrack by trackFlow.collectAsState(initial = trackSource)

    var isShowSheet by remember { mutableStateOf(false) }

    val isFavorite = currentTrack?.favorite ?: false


    val largeArtworkUrl = trackSource.image?.replace("100x100", "600x600")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PanelHeader(
            title = "",
            isButtonEnabled = true,
            onBack = onBack,
        )
        AsyncImage(
            modifier = Modifier.fillMaxWidth().width(312.dp).padding(end = 24.dp, start = 24.dp, top = 26.dp).clip(
                RoundedCornerShape(8.dp)
            ),
            model = largeArtworkUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_music),
            error = painterResource(R.drawable.ic_music),
            fallback = painterResource(R.drawable.ic_music),
        )
        Text(
            modifier = Modifier.padding(end = 24.dp, start = 24.dp, top = 24.dp),
            text = trackSource.trackName,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
            textAlign = TextAlign.Start,
        )
        Text(
            modifier = Modifier.padding(end = 24.dp, start = 24.dp, top = 12.dp),
            text = trackSource.artistName ,
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
                shape = CircleShape,
                onClick = {
                    trackDetailsViewModel.updateTrackFavoriteStatus(trackSource)
                    refreshTrigger++

                },
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_favorite),
                    contentDescription = null,
                    tint = if (isFavorite) Color.Red else Color.Unspecified
                )
            }
            PlaylistBottomSheet(
                isShowPanel = isShowSheet,
                onDismissRequest = { isShowSheet = false },
                playlistsViewModel = playlistsViewModel,
                track = currentTrack
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
                text = trackSource.trackTime ?: "",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
            )
        }
    }
}