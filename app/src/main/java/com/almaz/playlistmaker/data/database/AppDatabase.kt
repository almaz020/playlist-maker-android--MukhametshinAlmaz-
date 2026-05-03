package com.almaz.playlistmaker.data.database

import android.annotation.SuppressLint
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.almaz.playlistmaker.data.Playlist
import com.almaz.playlistmaker.data.database.dao.PlaylistsDao
import com.almaz.playlistmaker.data.database.dao.TracksDao
import com.almaz.playlistmaker.data.database.entity.PlaylistEntity
import com.almaz.playlistmaker.data.database.entity.TrackEntity
import com.almaz.playlistmaker.data.network.Track

@SuppressLint("RestrictedApi")
@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TracksDao(): TracksDao
    abstract fun PlaylistsDao(): PlaylistsDao
}

fun TrackEntity.toTrack(): Track {
    return Track(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        favorite = this.favorite,
        image = this.image,
        playlistId = this.playlistId
    )
}

fun Track.toEntity(): TrackEntity {
    return TrackEntity(
        id = this.id,
        trackName = this.trackName,
        artistName = this.artistName,
        trackTime = this.trackTime,
        image = this.image,
        favorite = this.favorite,
        playlistId = this.playlistId
    )
}

fun PlaylistEntity.toPlaylist(): Playlist {
    return Playlist(
        id = this.id,
        name = this.name,
        description = this.description,
        tracks = this.tracks
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        tracks = this.tracks
    )
}