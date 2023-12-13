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
import com.androidsquad.tunestream.services.model.AuthCode
import com.androidsquad.tunestream.services.model.AuthToken
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class LauncherViewModel(private val authAPI: SpotifyAuthAPI, private val cache: Cache) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val authTokenState = MutableLiveData<APIState>()

    fun fetchAuthToken(authCode: AuthCode? = null) {
        val token = cache.fetchAuthToken()
        Timber.i("fetchAuthToken: %s", token?.access_token)
//        if (token != null) {
//            authTokenState.value = APIState.DataState(token)
//            return
//        }
        if (authCode == null) return
        compositeDisposable.add(
            authAPI.getAuthToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                authCode.code,
                BuildConfig.REDIRECT_URL
            )
                .subscribe({ state ->
                    authTokenState.value = state
                    if (state is APIState.DataState<*>) cache.saveAuthToken(state.data as AuthToken)
                }, { exception ->
                    authTokenState.value = APIState.ErrorState(exception.message ?: "")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
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