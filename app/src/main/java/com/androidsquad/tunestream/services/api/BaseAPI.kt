package com.androidsquad.tunestream.services.api

import retrofit2.Retrofit

abstract class BaseAPI(private val baseUrl: String) {
    protected lateinit var retrofit: Retrofit

    init {
        initializeRetrofit()
    }

    private fun initializeRetrofit() {
        retrofit = RetrofitFactory.createRetrofitBuilder().baseUrl(baseUrl).build()
    }
}