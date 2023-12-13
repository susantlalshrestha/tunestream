package com.androidsquad.tunestream.features.dashboard.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.androidsquad.tunestream.databinding.FragmentSearchBinding
import com.androidsquad.tunestream.features.base.BaseFragment
import com.androidsquad.tunestream.features.dashboard.adapters.GenresItemAdapter
import com.androidsquad.tunestream.features.dashboard.viewmodels.RecommendationViewModel
import com.androidsquad.tunestream.features.launch.LauncherActivity
import com.androidsquad.tunestream.services.api.APIState
import com.androidsquad.tunestream.services.api.rp.GenresRP
import com.androidsquad.tunestream.services.api.rp.ListRp
import com.androidsquad.tunestream.services.model.Genre
import timber.log.Timber

class SearchFragment(
    private val recommendationViewModel: RecommendationViewModel
) : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var genresItemAdapter: GenresItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommendationViewModel.fetchGenres()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        genresItemAdapter = GenresItemAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = GridLayoutManager(context,2)
        binding.recyclerView.adapter = genresItemAdapter

        observeGenresState()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun observeGenresState() {
        recommendationViewModel.genresAPIState.observe(viewLifecycleOwner) { state ->
            state?.let { apiState ->
                when (apiState) {
                    is APIState.DataState<*> -> {
                        val genresRP = apiState.data as GenresRP
                        genresItemAdapter.genres.addAll(genresRP.categories.items)
                        genresItemAdapter.notifyDataSetChanged()
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