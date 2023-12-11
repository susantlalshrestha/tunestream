package com.androidsquad.tunestream.features.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.databinding.ActivityDashboardBinding
import com.androidsquad.tunestream.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}