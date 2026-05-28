package com.almaz.playlistmaker.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.almaz.playlistmaker.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("DELETE FROM tracks WHERE playlistId = :playlistId")
    suspend fun deleteTracksByPlaylistId(playlistId: Long)

    @Query("UPDATE tracks SET favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getTrackById(id: Long): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE id = :id")
    suspend fun getTrackOnce(id: Long): TrackEntity?

    @Query("SELECT * FROM tracks WHERE playlistId = :playlistId")
    fun getTracksForPlaylist(
        playlistId: Long
    ): Flow<List<TrackEntity>>

    @Query("SELECT COUNT(*) FROM tracks WHERE playlistId = :playlistId")
    fun getTracksCount(playlistId: Long): Flow<Int>

    @Query("DELETE FROM tracks WHERE id = :id AND playlistId = 0")
    suspend fun deleteTrackById(id: Long)
}

