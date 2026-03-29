package com.almaz.playlistmaker.ui.playlist

enum class PlaylistScreenEnum(val route: String) {
    Main("main_screen"),
    Search("search_screen"),
    Settings("settings_screen"),
    Playlists("playlists_screen"),
    Favorites("favorites_screen"),
    NewPlaylist("newPlaylist_screen"),
    TrackDetailsScreen("trackDetailsScreen"),

    Playlist("playlistScreen/{playlistId}")
}