package com.androidsquad.tunestream.features.launch.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.androidsquad.tunestream.BuildConfig
import com.androidsquad.tunestream.TuneStream
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.api.SpotifyAuthAPI
import com.androidsquad.tunestream.services.cache.Cache

class LauncherViewModel(private val authAPI: SpotifyAuthAPI, private val cache: Cache) :
    ViewModel() {

    val authTokenState = MutableLiveData<APIState>()

    fun fetchAuthToken() {
        val token = cache.fetchAuthToken()
        if (token != null) {
            authTokenState.value = APIState.DataState(token)
            return
        }
        authAPI.getAuthToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
            .subscribe { authTokenState.value = it }
    }

    override fun onCleared() {
        super.onCleared()
        authTokenState.value = APIState.FinishedState
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as TuneStream
                return LauncherViewModel(application.spotifyAuthAPI, application.cache) as T
            }
        }
    }
}