package com.androidsquad.tunestream.features.launch

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.databinding.ActivityLaunchBinding
import com.androidsquad.tunestream.features.dashboard.DashboardActivity
import com.androidsquad.tunestream.features.launch.viewmodels.LauncherViewModel
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.model.AuthToken

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    private val launcherViewModel: LauncherViewModel by viewModels { LauncherViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcherViewModel.fetchAuthToken()
        observeAuthTokenState()
    }

    private fun observeAuthTokenState() {
        launcherViewModel.authTokenState.observe(this) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        DashboardActivity.start(this)
                        finish()
                    }

                    is APIState.ErrorState -> {}
                    APIState.FinishedState -> {}
                    APIState.ProgressingState -> {}
                }
            }
        }
    }
}