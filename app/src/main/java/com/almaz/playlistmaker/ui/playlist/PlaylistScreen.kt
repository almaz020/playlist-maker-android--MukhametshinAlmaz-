package com.almaz.playlistmaker.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.ui.PanelHeader
import com.almaz.playlistmaker.ui.TrackListItem
import com.almaz.playlistmaker.ui.view_model.PlaylistViewModel
import kotlin.math.ceil

@Composable
fun PlaylistScreen(
    playlistViewModel: PlaylistViewModel,
    onClick: (Track) -> Unit,
    onBack: () -> Unit,
) {

    val playlist by playlistViewModel
        .playlist
        .collectAsState(null)

    val tracks by playlistViewModel
        .tracks
        .collectAsState(emptyList())

    // считаем общее время
    val totalMinutesRoundedUp: Int = tracks
        .map { track ->

            track.trackTime.split(":").let { parts ->

                val minutes =
                    parts.getOrNull(0)?.toIntOrNull() ?: 0

                val seconds =
                    parts.getOrNull(1)?.toIntOrNull() ?: 0

                minutes * 60 + seconds
            }
        }
        .sum()
        .let { totalSeconds ->
            ceil(totalSeconds / 60.0).toInt()
        }

    Column {

        PanelHeader(
            title = "",
            isButtonEnabled = true,
            onBack = onBack
        )

        Image(
            modifier = Modifier.padding(
                top = 132.dp,
                start = 130.dp,
                end = 130.dp
            ),
            painter = painterResource(R.drawable.add_photo),
            contentDescription = null,
        )

        playlist?.let {

            Column {

                Text(
                    text = it.name,
                    fontFamily = FontFamily(
                        Font(R.font.yandexsanstextmedium)
                    ),
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    text = "2026",
                    fontFamily = FontFamily(
                        Font(R.font.yandexsanstextregular)
                    ),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )

                Row(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 8.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "$totalMinutesRoundedUp минут",
                        fontFamily = FontFamily(
                            Font(R.font.yandexsanstextregular)
                        ),
                        fontSize = 18.sp,
                    )

                    Image(
                        modifier = Modifier.padding(
                            start = 5.dp,
                            top = 9.dp,
                            bottom = 9.dp,
                            end = 5.dp
                        ),
                        painter = painterResource(R.drawable.dot),
                        contentDescription = null,
                    )

                    Text(
                        text = "${tracks.size} треков",
                        fontFamily = FontFamily(
                            Font(R.font.yandexsanstextregular)
                        ),
                        fontSize = 18.sp,
                    )
                }

                Image(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        start = 26.dp,
                        bottom = 20.dp
                    ),
                    painter = painterResource(R.drawable.dot3),
                    contentDescription = null
                )

                LazyColumn {

                    items(tracks) { track ->

                        TrackListItem(
                            track = track,
                            onClick = onClick
                        )
                    }
                }
            }
        }
    }
}