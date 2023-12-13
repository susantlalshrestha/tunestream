package com.androidsquad.tunestream.services.api

import com.androidsquad.tunestream.services.api.apiservices.RecommendationAPIService
import com.androidsquad.tunestream.services.api.apiservices.UserAPIService
import com.androidsquad.tunestream.services.model.AuthToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SpotifyAPI : BaseAPI(BASE_URL) {
    private val userApiService: UserAPIService = retrofit.create(UserAPIService::class.java)
    private val recommendationAPIService: RecommendationAPIService = retrofit.create(RecommendationAPIService::class.java)

    fun getUserProfile(authToken: AuthToken): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return userApiService.getUserProfile(token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    fun getRecentlyPlayedTracks(
        authToken: AuthToken,
        limit: Int = 10,
        after: String? = null
    ): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return userApiService.getRecentlyPlayedTracks(token, limit, after)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    fun getTopTracks(authToken: AuthToken): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return userApiService.getTopTracks(token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    fun getMyPlaylists(authToken: AuthToken): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return userApiService.getMyPlaylists(token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    fun getPlaylistsByGenre(genreId: String, authToken: AuthToken): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return userApiService.getPlaylistsByGenre(genreId, token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    fun getRecommendationTracks(authToken: AuthToken): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return recommendationAPIService.getRecommendations(token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    fun getGenres(authToken: AuthToken): Observable<APIState> {
        val token = "${authToken.token_type} ${authToken.access_token}"
        return recommendationAPIService.getGenres(token)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map<APIState> { APIState.DataState(it) }
            .startWithItem(APIState.ProgressingState)
            .onErrorReturn { APIState.ErrorState(it.message!!) }
    }

    companion object {
        const val BASE_URL = "https://api.spotify.com/v1/"
    }
}