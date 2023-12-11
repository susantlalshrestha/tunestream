package com.androidsquad.tunestream.services.api

import com.androidsquad.tunestream.services.api.apiservices.AuthAPIService

class SpotifyAPI : BaseAPI(BASE_URL) {
    private val apiService: AuthAPIService = retrofit.create(AuthAPIService::class.java)

    companion object {
        const val BASE_URL = "https://api.spotify.com/v1/"
    }
}