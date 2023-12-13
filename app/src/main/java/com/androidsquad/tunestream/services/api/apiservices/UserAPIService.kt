package com.androidsquad.tunestream.services.api.apiservices

import com.androidsquad.tunestream.services.api.rp.ListRp
import com.androidsquad.tunestream.services.api.rp.PlaylistRP
import com.androidsquad.tunestream.services.model.Playlist
import com.androidsquad.tunestream.services.model.Track
import com.androidsquad.tunestream.services.model.TrackItem
import com.androidsquad.tunestream.services.model.UserProfile
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPIService {

    @GET("me")
    fun getUserProfile(
        @Header("Authorization") token: String,
    ): Observable<UserProfile>

    @GET("me/player/recently-played")
    fun getRecentlyPlayedTracks(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("after") after: String? = null
    ): Observable<ListRp<TrackItem>>

    @GET("me/top/tracks")
    fun getTopTracks(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
    ): Observable<ListRp<Track>>

    @GET("me/playlists")
    fun getMyPlaylists(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
    ): Observable<ListRp<Playlist>>

    @GET("browse/categories/{genreId}/playlists")
    fun getPlaylistsByGenre(
        @Path("genreId") genreId: String,
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 10,
    ): Observable<PlaylistRP>
}