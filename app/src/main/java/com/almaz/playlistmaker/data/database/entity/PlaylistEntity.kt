package com.almaz.playlistmaker.data.database.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.almaz.playlistmaker.data.network.Track

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    var tracks: List<Track>
)