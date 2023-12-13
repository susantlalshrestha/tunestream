package com.androidsquad.tunestream.features.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidsquad.tunestream.R
import com.androidsquad.tunestream.databinding.ActivityAuthBinding
import com.androidsquad.tunestream.features.auth.fragments.LoginFragment
import com.androidsquad.tunestream.features.auth.fragments.RegisterFragment

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val screenName = intent.getStringExtra(SCREEN_NAME)
        val fragment = if (screenName == AuthScreens.LOGIN.name) LoginFragment()
        else RegisterFragment()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    companion object {
        private const val SCREEN_NAME = "AUTH_SCREEN_NAME"
        fun start(context: Context, screen: AuthScreens = AuthScreens.LOGIN) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.putExtra(SCREEN_NAME, screen.name)
            context.startActivity(intent)
        }
    }
}
