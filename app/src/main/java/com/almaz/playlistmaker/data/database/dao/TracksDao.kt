package com.almaz.playlistmaker.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.almaz.playlistmaker.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity?)

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>
}