package com.androidsquad.tunestream.features.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.databinding.ActivityDashboardBinding
import com.androidsquad.tunestream.features.base.BaseActivity
import com.androidsquad.tunestream.features.dashboard.fragments.HomeFragment
import com.androidsquad.tunestream.features.dashboard.fragments.LibraryFragment
import com.androidsquad.tunestream.features.dashboard.fragments.ProfileFragment
import com.androidsquad.tunestream.features.dashboard.fragments.SearchFragment
import com.androidsquad.tunestream.features.dashboard.viewmodels.RecommendationViewModel
import com.androidsquad.tunestream.features.dashboard.viewmodels.UserViewModel

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private val userViewModel: UserViewModel by viewModels { UserViewModel.Factory }
    private val recommendationViewModel: RecommendationViewModel by viewModels { RecommendationViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment(userViewModel, recommendationViewModel)
        val searchFragment = SearchFragment(recommendationViewModel)
        val libraryFragment = LibraryFragment(userViewModel)
        val profileFragment = ProfileFragment(userViewModel)

        binding.btmNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btm_home -> setCurrentFragment(homeFragment)
                R.id.btm_search -> setCurrentFragment(searchFragment)
                R.id.btm_library -> setCurrentFragment(libraryFragment)
                R.id.btm_profile -> setCurrentFragment(profileFragment)
            }
            true
        }
        binding.btmNavView.selectedItemId = R.id.btm_home
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, DashboardActivity::class.java))
        }
    }
}