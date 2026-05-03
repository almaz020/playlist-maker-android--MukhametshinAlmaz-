package com.almaz.playlistmaker.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.almaz.playlistmaker.data.database.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistsDao {
    @Query("SELECT * FROM playlists")
    fun getPlaylists() : Flow<List<PlaylistEntity?>>
}