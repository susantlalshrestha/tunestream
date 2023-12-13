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

class ProfileViewModel(
    private val spotifyAuthAPI: SpotifyAuthAPI,
    private val spotifyAPI: SpotifyAPI,
    private val cache: Cache
) :
    ViewModel() {
    val userProfileAPIState = MutableLiveData<APIState>()

    fun fetchUserProfile() {
        val token = cache.fetchAuthToken()
    }
//    BQC1w9VL82VBRij_1s4ljWiE8hDbbapRXoGIL33HzQTcrfB2hgXOZEJa0uiq1AbwavlCzSTaGlYNJCI6eQ3hq3w6zL7uMP2Xkq5sAhrGquY5n2DZcIU

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as TuneStream
                return ProfileViewModel(
                    application.spotifyAuthAPI,
                    application.spotifyAPI,
                    application.cache
                ) as T
            }
        }
    }
}