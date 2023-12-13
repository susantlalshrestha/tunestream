package com.androidsquad.tunestream.services.api

import android.util.Base64
import com.androidsquad.tunestream.services.api.apiservices.AuthAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


class SpotifyAuthAPI : BaseAPI(BASE_URL) {
    private val authAPIService: AuthAPIService = retrofit.create(AuthAPIService::class.java);

    fun getAuthToken(
        clientId: String,
        clientSecret: String,
        code: String,
        redirectUrl: String
    ): Observable<APIState> {
        val data = "$clientId:$clientSecret".toByteArray()
        val base64 = Base64.encodeToString(data, Base64.NO_WRAP)
        val token = "Basic $base64"
        return authAPIService.getAuthToken(token, code, redirectUrl)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    companion object {
        const val BASE_URL = "https://accounts.spotify.com/"
    }
}