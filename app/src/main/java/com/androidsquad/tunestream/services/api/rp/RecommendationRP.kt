package com.androidsquad.tunestream.services.api.rp

import com.androidsquad.tunestream.services.model.Track

data class RecommendationRP(
    val tracks: ArrayList<Track>
)