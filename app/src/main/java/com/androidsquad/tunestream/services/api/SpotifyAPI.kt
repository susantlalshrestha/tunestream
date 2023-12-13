package com.androidsquad.tunestream.services.api

import com.androidsquad.tunestream.services.api.apiservices.UserAPIService

class SpotifyAPI : BaseAPI(BASE_URL) {
    private val userApiService: UserAPIService = retrofit.create(UserAPIService::class.java)

    companion object {
        const val BASE_URL = "https://api.spotify.com/v1/"
    }
}