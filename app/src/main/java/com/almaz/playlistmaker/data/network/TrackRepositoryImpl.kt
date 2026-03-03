package com.almaz.playlistmaker.data.network

import com.almaz.playlistmaker.domain.NetworkClient
import com.almaz.playlistmaker.data.dto.TrackSearchRequest
import com.almaz.playlistmaker.data.dto.TrackSearchResponse
import com.almaz.playlistmaker.data.network.Track
import com.almaz.playlistmaker.domain.TracksRepository
import kotlinx.coroutines.delay

class TracksRepositoryImpl() : TracksRepository {
    override suspend fun getAllTracks(): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) }
    }
}

val listTracks = listOf(
    Track(
        id = 1,
        trackName = "Shape of You",
        artistName = "Ed Sheeran",
        trackTime = "3:53",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 2,
        trackName = "Blinding Lights",
        artistName = "The Weeknd",
        trackTime = "3:20",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 3,
        trackName = "Levitating",
        artistName = "Dua Lipa",
        trackTime = "3:23",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 4,
        trackName = "Bad Guy",
        artistName = "Billie Eilish",
        trackTime = "3:14",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 5,
        trackName = "Stay",
        artistName = "Justin Bieber",
        trackTime = "2:21",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 6,
        trackName = "Circles",
        artistName = "Post Malone",
        trackTime = "3:35",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 7,
        trackName = "Rolling in the Deep",
        artistName = "Adele",
        trackTime = "3:48",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 8,
        trackName = "Senorita",
        artistName = "Shawn Mendes & Camila Cabello",
        trackTime = "3:11",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 9,
        trackName = "Uptown Funk",
        artistName = "Mark Ronson ft. Bruno Mars",
        trackTime = "4:30",
        image = "",
        favorite = false,
        playlistId = 0
    ),
    Track(
        id = 10,
        trackName = "Someone You Loved",
        artistName = "Lewis Capaldi",
        trackTime = "3:02",
        image = "",
        favorite = false,
        playlistId = 0
    )
)