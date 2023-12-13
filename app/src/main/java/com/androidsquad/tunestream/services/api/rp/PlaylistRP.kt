package com.androidsquad.tunestream.services.api.rp

import com.androidsquad.tunestream.services.model.Playlist

data class PlaylistRP (
    val playlists: ListRp<Playlist>
)