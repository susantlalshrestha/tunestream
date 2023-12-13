package com.androidsquad.tunestream.features.dashboard.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidsquad.tunestream.databinding.FragmentProfileBinding
import com.androidsquad.tunestream.features.base.BaseFragment
import com.androidsquad.tunestream.features.dashboard.viewmodels.UserViewModel
import com.androidsquad.tunestream.features.launch.LauncherActivity
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.model.UserProfile

class ProfileFragment(private val userViewModel: UserViewModel) : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.fetchUserProfile()
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
        userViewModel.userProfileAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val profile = apiState.data as UserProfile
                        binding.tvProfileInitial.text = profile.display_name[0].toString()
                        binding.tvProfileName.text = profile.display_name
                        binding.tvProfileEmail.text = profile.email
                    }

                    is APIState.ErrorState -> {
                        if (apiState.error == "logout") {
                            activity?.let {
                                LauncherActivity.start(it)
                                it.finish()
                            }
                        }
                        showMessage(apiState.error)
                    }

                    APIState.FinishedState -> {}
                    APIState.ProgressingState -> {}
                }
            }
        }
    }
}