package com.androidsquad.tunestream.services.cache

import android.content.SharedPreferences
import com.androidsquad.tunestream.services.model.AuthCode
import com.androidsquad.tunestream.services.model.AuthToken
import com.google.gson.Gson

class Cache(private val sharedPreferences: SharedPreferences) {

    fun saveAuthToken(token: AuthToken) {
        val editor = sharedPreferences.edit()
        editor.putString(AUTH_TOKEN, Gson().toJson(token))
        editor.apply()
    }

    fun fetchAuthToken(): AuthToken? {
        val authToken = sharedPreferences.getString(AUTH_TOKEN, null)
        return Gson().fromJson(authToken, AuthToken::class.java)
    }

    companion object {
        private const val AUTH_TOKEN = "Cache.AUTH_TOKEN"
        private const val AUTH_CODE = "Cache.AUTH_CODE"
    }
}