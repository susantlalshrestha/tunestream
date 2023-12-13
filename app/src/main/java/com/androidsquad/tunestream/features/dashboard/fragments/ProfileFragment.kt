package com.androidsquad.tunestream.features.dashboard.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidsquad.tunestream.databinding.FragmentProfileBinding
import com.androidsquad.tunestream.features.dashboard.viewmodels.ProfileViewModel
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.model.UserProfile

class ProfileFragment(private val profileViewModel: ProfileViewModel) : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.fetchUserProfile()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserProfileState()
    }

    private fun observeUserProfileState() {
        profileViewModel.userProfileAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val profile = apiState.data as UserProfile
                        Log.i("TAG", "observeUserProfileState: " + profile.display_name)
                    }

                    is APIState.ErrorState -> {
                        Log.i("TAG", "observeUserProfileState: " + apiState.error)}
                    APIState.FinishedState -> {}
                    APIState.ProgressingState -> {}
                }
            }
        }
    }
}