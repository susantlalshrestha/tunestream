package com.androidsquad.tunestream

import android.app.Application
import com.androidsquad.tunestream.services.api.SpotifyAPI
import com.androidsquad.tunestream.services.api.SpotifyAuthAPI
import com.androidsquad.tunestream.services.cache.Cache
import timber.log.Timber


class TuneStream : Application() {
    lateinit var spotifyAPI: SpotifyAPI
    lateinit var spotifyAuthAPI: SpotifyAuthAPI
    lateinit var cache: Cache
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
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