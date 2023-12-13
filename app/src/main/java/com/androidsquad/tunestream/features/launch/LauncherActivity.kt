package com.androidsquad.tunestream.features.launch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.androidsquad.tunestream.BuildConfig
import com.androidsquad.tunestream.databinding.ActivityLaunchBinding
import com.androidsquad.tunestream.features.base.BaseActivity
import com.androidsquad.tunestream.features.dashboard.DashboardActivity
import com.androidsquad.tunestream.features.launch.viewmodels.LauncherViewModel
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.model.AuthCode
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import timber.log.Timber

class LauncherActivity : BaseActivity() {

    private lateinit var binding: ActivityLaunchBinding

    private val launcherViewModel: LauncherViewModel by viewModels { LauncherViewModel.Factory }

    private lateinit var spotifyAuthLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBegin.setOnClickListener { authorizeSpotify() }

        registerSpotifyAuthLauncher()
        observeAuthTokenState()
        launcherViewModel.fetchAuthToken()
    }

    private fun registerSpotifyAuthLauncher() {
        spotifyAuthLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val response = AuthorizationClient.getResponse(result.resultCode, result.data)
                when (response.type) {
                    AuthorizationResponse.Type.CODE -> {
                        val authCode = AuthCode(response.code, response.state)
                        launcherViewModel.fetchAuthToken(authCode)
                    }

                    AuthorizationResponse.Type.ERROR -> showMessage(response.error)
                    else -> showMessage("Failed to authorize spotify api")
                }
            } else {
                showMessage("Failed to authorize spotify api")
            }
        }
    }

    private fun authorizeSpotify() {
        val clientId = BuildConfig.CLIENT_ID
        val responseType = AuthorizationResponse.Type.CODE
        val redirectUrl = BuildConfig.REDIRECT_URL
        val scopes = arrayOf(
            "user-read-private",
            "user-read-email",
            "user-follow-modify",
            "user-follow-read",
            "user-read-playback-position",
            "user-top-read",
            "user-read-recently-played",
            "user-library-modify",
            "user-library-read",
            "user-read-playback-state",
            "user-modify-playback-state",
            "user-read-currently-playing",
            "app-remote-control",
            "streaming",
            "playlist-read-private",
            "playlist-read-collaborative",
            "playlist-modify-private",
            "playlist-modify-public",
        )
        val request =
            AuthorizationRequest.Builder(clientId, responseType, redirectUrl).setScopes(scopes)
                .build()
        val intent = AuthorizationClient.createLoginActivityIntent(this, request)
        spotifyAuthLauncher.launch(intent)
    }

    private fun observeAuthTokenState() {
        launcherViewModel.authTokenState.observe(this) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        DashboardActivity.start(this)
                        finish()
                    }

                    is APIState.ErrorState -> showMessage(apiState.error)
                    APIState.FinishedState -> {}
                    APIState.ProgressingState -> {}
                }
            }
        }
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LauncherActivity::class.java))
        }
    }
}