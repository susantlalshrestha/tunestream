package com.androidsquad.tunestream.services.api

import com.androidsquad.tunestream.services.api.apiservices.AuthAPIService
import com.androidsquad.tunestream.services.model.AuthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpotifyAuthAPI : BaseAPI(BASE_URL) {
    private val authAPIService: AuthAPIService = retrofit.create(AuthAPIService::class.java);

    fun getAuthToken(clientId: String, clientSecret: String): APIObservable<APIState> {
        val grantType = "client_credentials"
        return object : APIObservable<APIState> {
            override fun subscribe(subscriber: (state: APIState) -> Unit) {
                subscriber(APIState.ProgressingState)
                authAPIService.getToken(grantType, clientId, clientSecret)
                    .enqueue(object : Callback<AuthToken> {
                        override fun onResponse(
                            call: Call<AuthToken>,
                            response: Response<AuthToken>
                        ) {
                            val token = response.body()
                            if (token == null) {
                                subscriber(APIState.ErrorState("Failed to fetch auth token!"))
                                return
                            }
                            subscriber(APIState.DataState(token))
                        }

                        override fun onFailure(call: Call<AuthToken>, t: Throwable) {
                            subscriber(APIState.ErrorState(t.message!!))
                        }
                    })
            }
        }
    }

    companion object {
        const val BASE_URL = "https://accounts.spotify.com/api/"
    }
}