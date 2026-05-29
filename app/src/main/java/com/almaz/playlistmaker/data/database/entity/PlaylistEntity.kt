package com.almaz.playlistmaker.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.almaz.playlistmaker.data.network.Track

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String
)