package com.androidsquad.tunestream.services.api.rp

import com.androidsquad.tunestream.services.model.Genre

data class GenresRP (
    val categories: ListRp<Genre>
)