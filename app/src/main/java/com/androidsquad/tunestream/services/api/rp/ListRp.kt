package com.androidsquad.tunestream.services.api.rp

import com.androidsquad.tunestream.services.model.Cursors

data class ListRp<D>(
    val href: String,
    val limit: Int,
    val next: String,
    val cursors: Cursors?,
    val offset: Int?,
    val previous: String?,
    val total: Int,
    val items: ArrayList<D>
)