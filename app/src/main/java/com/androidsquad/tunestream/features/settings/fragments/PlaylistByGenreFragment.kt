package com.androidsquad.tunestream.features.settings.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidsquad.tunestream.databinding.FragmentLibraryBinding
import com.androidsquad.tunestream.features.base.BaseFragment
import com.androidsquad.tunestream.features.dashboard.adapters.PlaylistItemAdapter
import com.androidsquad.tunestream.features.dashboard.viewmodels.UserViewModel
import com.androidsquad.tunestream.features.launch.LauncherActivity
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.api.rp.ListRp
import com.androidsquad.tunestream.services.api.rp.PlaylistRP
import com.androidsquad.tunestream.services.model.Playlist

class PlaylistByGenreFragment(private val userViewModel: UserViewModel, val genreId: String, val genreName: String) : BaseFragment() {
    private lateinit var binding: FragmentLibraryBinding

    private lateinit var playlistAdapter: PlaylistItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.fetchPlaylistsByGenre(genreId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        playlistAdapter = PlaylistItemAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = playlistAdapter

        binding.tvTitle.text = genreName

        observeGenresState()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeGenresState() {
        userViewModel.playlistByGenreAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val playlists = apiState.data as PlaylistRP
                        playlistAdapter.playlists.addAll(playlists.playlists.items)
                        playlistAdapter.notifyDataSetChanged()
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