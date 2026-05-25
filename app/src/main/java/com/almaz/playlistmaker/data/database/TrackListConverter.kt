package com.almaz.playlistmaker.data.database

import androidx.room.TypeConverter
import com.almaz.playlistmaker.data.database.entity.TrackEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.almaz.playlistmaker.data.network.Track

class TrackListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromTracks(list: List<TrackEntity>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toTracks(json: String): List<TrackEntity> {
        val type = object : TypeToken<List<TrackEntity>>() {}.type
        return gson.fromJson(json, type)
    }
}