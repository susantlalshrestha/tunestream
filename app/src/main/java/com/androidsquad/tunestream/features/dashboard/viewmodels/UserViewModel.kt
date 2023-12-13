package com.androidsquad.tunestream.features.dashboard.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.androidsquad.tunestream.TuneStream
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.api.SpotifyAPI
import com.androidsquad.tunestream.services.api.SpotifyAuthAPI
import com.androidsquad.tunestream.services.cache.Cache
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UserViewModel(
    private val spotifyAuthAPI: SpotifyAuthAPI,
    private val spotifyAPI: SpotifyAPI,
    private val cache: Cache
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val userProfileAPIState = MutableLiveData<APIState>()
    val recentTracksAPIState = MutableLiveData<APIState>()
    val topTracksAPIState = MutableLiveData<APIState>()
    val myPlaylistAPIState = MutableLiveData<APIState>()
    val playlistByGenreAPIState = MutableLiveData<APIState>()

    fun fetchUserProfile() {
        val token = cache.fetchAuthToken()
        if (token == null) {
            userProfileAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getUserProfile(token)
                .subscribe({ state ->
                    userProfileAPIState.value = state
                }, { exception ->
                    userProfileAPIState.value = APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    fun fetchRecentlyPlayedTracks(limit: Int = 10, after: String? = null) {
        val token = cache.fetchAuthToken()
        if (token == null) {
            recentTracksAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getRecentlyPlayedTracks(token, limit, after)
                .subscribe({ state ->
                    recentTracksAPIState.value = state
                }, { exception ->
                    recentTracksAPIState.value = APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    fun fetchTopTracks() {
        val token = cache.fetchAuthToken()
        if (token == null) {
            topTracksAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getRecentlyPlayedTracks(token)
                .subscribe({ state ->
                    topTracksAPIState.value = state
                }, { exception ->
                    topTracksAPIState.value = APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    fun fetchMyPlaylists() {
        val token = cache.fetchAuthToken()
        if (token == null) {
            topTracksAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getMyPlaylists(token)
                .subscribe({ state ->
                    myPlaylistAPIState.value = state
                }, { exception ->
                    myPlaylistAPIState.value = APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    fun fetchPlaylistsByGenre(genreId: String) {
        val token = cache.fetchAuthToken()
        if (token == null) {
            topTracksAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getPlaylistsByGenre(genreId, token)
                .subscribe({ state ->
                    playlistByGenreAPIState.value = state
                }, { exception ->
                    playlistByGenreAPIState.value = APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        userProfileAPIState.value = APIState.FinishedState
        recentTracksAPIState.value = APIState.FinishedState
        topTracksAPIState.value = APIState.FinishedState
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as TuneStream
                return UserViewModel(
                    application.spotifyAuthAPI,
                    application.spotifyAPI,
                    application.cache
                ) as T
            }
        }
    }
}