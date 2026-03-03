package com.almaz.playlistmaker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.network.Track

@Composable
fun TrackListItem(
    track: Track,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .padding(top = 8.dp, start = 13.dp, bottom = 8.dp)
                .size(45.dp),
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Трек ${track.trackName}"
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(bottom = 1.dp),
                text = track.trackName,
                fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = track.artistName,
                    fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
                    fontSize = 11.sp,
                    color = colorResource(R.color.light_gray_for_search_field)
                )
                Image(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 5.dp,
                        end = 5.dp,
                        bottom = 5.dp
                    ),
                    painter = painterResource(R.drawable.ellipse),
                    contentDescription = null,
                )
                Text(
                    text = track.trackTime,
                    fontFamily = FontFamily(Font(R.font.yandexsanstextregular)),
                    fontSize = 11.sp,
                    color = colorResource(R.color.light_gray_for_search_field)
                )
            }
        }
        Box(
            modifier = Modifier.padding(
                top = 18.dp,
                end = 12.dp,
                bottom = 19.dp
            )
        ) {
            Image(
                modifier = Modifier
                    .padding(
                        top = 5.dp,
                        start = 8.dp,
                        end = 5.dp,
                        bottom = 8.dp
                    ),
                painter = painterResource(R.drawable.arrow),
                contentDescription = null,
            )
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewTrackListItem() {
    val track = Track("Песня", "Музыка", "1000")
    TrackListItem(track) {}

}