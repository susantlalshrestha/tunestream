package com.androidsquad.tunestream.services.model

import com.androidsquad.tunestream.services.model.Track

data class TrackItem(
    val track: Track,
    val played_at: String,
)