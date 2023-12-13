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

class RecommendationViewModel(
    private val spotifyAuthAPI: SpotifyAuthAPI,
    private val spotifyAPI: SpotifyAPI,
    private val cache: Cache
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val recommendationTracksAPIState = MutableLiveData<APIState>()
    val genresAPIState = MutableLiveData<APIState>()

    fun fetchRecommendationTracks() {
        val token = cache.fetchAuthToken()
        if (token == null) {
            recommendationTracksAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getRecommendationTracks(token)
                .subscribe({ state ->
                    recommendationTracksAPIState.value = state
                }, { exception ->
                    recommendationTracksAPIState.value =
                        APIState.ErrorState(exception.message ?: "")
                })
        )
    }


    fun fetchGenres() {
        val token = cache.fetchAuthToken()
        if (token == null) {
            recommendationTracksAPIState.value = APIState.ErrorState("logout")
            return
        }
        compositeDisposable.add(
            spotifyAPI.getGenres(token)
                .subscribe({ state -> genresAPIState.value = state }, { exception ->
                    genresAPIState.value =
                        APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        recommendationTracksAPIState.value = APIState.FinishedState
        genresAPIState.value = APIState.FinishedState
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as TuneStream
                return RecommendationViewModel(
                    application.spotifyAuthAPI,
                    application.spotifyAPI,
                    application.cache
                ) as T
            }
        }
    }
}