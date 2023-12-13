package com.androidsquad.tunestream.services.api.apiservices

import com.androidsquad.tunestream.services.model.UserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UserAPIService {

    @GET("me")
    fun getUserProfile(
        @Header("Authorization") token: String,
    ): Call<UserProfile>
}