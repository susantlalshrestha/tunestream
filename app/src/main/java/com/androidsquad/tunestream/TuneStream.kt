package com.androidsquad.tunestream

import android.app.Application
import android.content.SharedPreferences
import com.androidsquad.tunestream.services.api.SpotifyAPI
import com.androidsquad.tunestream.services.api.SpotifyAuthAPI
import com.androidsquad.tunestream.services.cache.Cache

class TuneStream : Application() {
    lateinit var spotifyAPI: SpotifyAPI
    lateinit var spotifyAuthAPI: SpotifyAuthAPI
    lateinit var cache: Cache
    override fun onCreate() {
        super.onCreate()
        initializeAPIServices()
        initializeCache()
    }

    private fun initializeAPIServices() {
        spotifyAPI = SpotifyAPI()
        spotifyAuthAPI = SpotifyAuthAPI()
    }

    private fun initializeCache() {
        val preferences = getSharedPreferences("TuneStreamPrefs", MODE_PRIVATE)
        cache = Cache(preferences)
    }
}