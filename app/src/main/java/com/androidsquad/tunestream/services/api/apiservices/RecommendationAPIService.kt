package com.androidsquad.tunestream.services.api.apiservices

import com.androidsquad.tunestream.services.api.rp.GenresRP
import com.androidsquad.tunestream.services.api.rp.ListRp
import com.androidsquad.tunestream.services.api.rp.RecommendationRP
import com.androidsquad.tunestream.services.model.Genre
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecommendationAPIService {

    @GET("recommendations")
    fun getRecommendations(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("market") market: String = "NP",
        @Query("seed_genres") seed_genres: String = "pop"
    ): Observable<RecommendationRP>

    @GET("browse/categories")
    fun getGenres(
        @Header("Authorization") token: String
    ): Observable<GenresRP>
}