package com.androidsquad.tunestream.features.dashboard.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidsquad.tunestream.databinding.FragmentHomeBinding
import com.androidsquad.tunestream.features.base.BaseFragment
import com.androidsquad.tunestream.features.dashboard.adapters.HomeItemAdapter
import com.androidsquad.tunestream.features.dashboard.viewmodels.RecommendationViewModel
import com.androidsquad.tunestream.features.dashboard.viewmodels.UserViewModel
import com.androidsquad.tunestream.features.launch.LauncherActivity
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.api.rp.ListRp
import com.androidsquad.tunestream.services.api.rp.RecommendationRP
import timber.log.Timber

class HomeFragment(
    private val userViewModel: UserViewModel,
    private val recommendationViewModel: RecommendationViewModel
) : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeItemAdapter: HomeItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.fetchRecentlyPlayedTracks()
        recommendationViewModel.fetchRecommendationTracks()
        userViewModel.fetchTopTracks()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeItemAdapter = HomeItemAdapter(arrayListOf())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = homeItemAdapter

        observeRecentTracksState()
        observeRecommendationTracksState()
        observeTopTracksState()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeRecentTracksState() {
        userViewModel.recentTracksAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val trackRp = apiState.data as ListRp<Any>
                        Timber.i("" + trackRp.total)
                        if (trackRp.items.isNotEmpty()) {
                            val homeItem =
                                HomeItemAdapter.HomeItem("Recently Played", trackRp.items)
                            homeItemAdapter.items.add(homeItem)
                            homeItemAdapter.notifyDataSetChanged()
                        }
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


    @SuppressLint("NotifyDataSetChanged")
    private fun observeRecommendationTracksState() {
        recommendationViewModel.recommendationTracksAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val recommendationRP = apiState.data as RecommendationRP
                        if (recommendationRP.tracks.isNotEmpty()) {
                            val homeItem =
                                HomeItemAdapter.HomeItem(
                                    "Top Recommendations",
                                    recommendationRP.tracks
                                )
                            homeItemAdapter.items.add(homeItem)
                            homeItemAdapter.notifyDataSetChanged()
                        }
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

    @SuppressLint("NotifyDataSetChanged")
    private fun observeTopTracksState() {
        userViewModel.topTracksAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val topTracks = apiState.data as ListRp<Any>
                        if (topTracks.items.isNotEmpty()) {
                            topTracks.items.reverse()
                            val homeItem =
                                HomeItemAdapter.HomeItem("Top Tracks", topTracks.items)
                            homeItemAdapter.items.add(homeItem)
                            homeItemAdapter.notifyDataSetChanged()
                        }
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