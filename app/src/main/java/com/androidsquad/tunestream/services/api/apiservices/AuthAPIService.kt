package com.androidsquad.tunestream.services.api.apiservices

import com.androidsquad.tunestream.services.model.AuthToken
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPIService {
    @FormUrlEncoded
    @POST("api/token")
    fun getAuthToken(
        @Header("Authorization") token: String,
        @Field("code") authCode: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String? = "authorization_code",
    ): Observable<AuthToken>

    @FormUrlEncoded
    @POST("api/token")
    fun refreshToken(
        @Field("grant_type") grantType: String?,
        @Field("refresh_token") refreshToken: String?,
        @Field("client_id") clientID: String?,
        @Field("scope") scope: String?
    ): Observable<AuthToken>
}