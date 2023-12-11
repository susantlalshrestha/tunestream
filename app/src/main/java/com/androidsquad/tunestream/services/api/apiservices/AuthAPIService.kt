package com.androidsquad.tunestream.services.api.apiservices

import com.androidsquad.tunestream.services.model.AuthToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthAPIService {
    @FormUrlEncoded
    @POST("token")
    fun getToken(
        @Field("grant_type") grantType: String?,
        @Field("client_id") clientID: String?,
        @Field("client_secret") clientSecret: String?
    ): Call<AuthToken>
}