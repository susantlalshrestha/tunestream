package com.androidsquad.tunestream.features.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.databinding.ActivityDashboardBinding
import com.androidsquad.tunestream.databinding.ActivitySettingsBinding
import com.androidsquad.tunestream.features.dashboard.DashboardActivity
import com.androidsquad.tunestream.features.dashboard.viewmodels.UserViewModel
import com.androidsquad.tunestream.features.settings.fragments.PlaylistByGenreFragment

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val userViewModel: UserViewModel by viewModels { UserViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genreId = intent.getStringExtra(GENRE_ID)
        val genreName = intent.getStringExtra(GENRE_NAME)

        if (genreId != null && genreName != null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    PlaylistByGenreFragment(userViewModel, genreId, genreName)
                ).commit()
        }
    }

    companion object {
        const val GENRE_ID = "GENRE_ID"
        const val GENRE_NAME = "GENRE_NAME"
        fun start(context: Context, genreId: String, genreName: String) {
            val intent = Intent(context, SettingsActivity::class.java)
            intent.putExtra(GENRE_ID, genreId)
            intent.putExtra(GENRE_NAME, genreName)
            context.startActivity(intent)
        }
    }
}