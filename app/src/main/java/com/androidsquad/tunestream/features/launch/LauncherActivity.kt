package com.androidsquad.tunestream.features.launch

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.features.launch.viewmodels.LauncherViewModel
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.model.AuthToken

class LauncherActivity : AppCompatActivity() {

    private val launcherViewModel: LauncherViewModel by viewModels { LauncherViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        launcherViewModel.fetchAuthToken()
        observeAuthTokenState()
    }

    private fun observeAuthTokenState() {
        launcherViewModel.authTokenState.observe(this) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val token = apiState.data as AuthToken
                        Log.i("TAG", "observeAuthTokenState: "+ token.access_token)
                    }
                    is APIState.ErrorState -> {}
                    APIState.FinishedState -> {}
                    APIState.ProgressingState -> {}
                }
            }
        }
    }
}