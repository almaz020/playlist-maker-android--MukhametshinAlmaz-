package com.almaz.playlistmaker.data.database.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String?,
    val favorite: Boolean = false,
    val playlistId: Long
)