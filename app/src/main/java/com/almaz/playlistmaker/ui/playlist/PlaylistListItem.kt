package com.almaz.playlistmaker.ui.playlist

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.almaz.playlistmaker.R
import com.almaz.playlistmaker.data.Playlist
import java.io.File

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    tracksCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (playlist.coverImageUri != null) {

            AsyncImage(
                model = File(playlist.coverImageUri),
                contentDescription = playlist.name,
                modifier = Modifier.size(48.dp),
            )
        } else {
            // Показываем плейсхолдер
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.add_photo),
                contentDescription = playlist.name,
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = playlist.name,
                fontSize = 16.sp
            )

            Text(
                text = "$tracksCount tracks",
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}
