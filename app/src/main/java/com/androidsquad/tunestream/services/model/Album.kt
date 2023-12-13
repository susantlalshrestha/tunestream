package com.androidsquad.tunestream.services.model

data class Album(
    val album_type: String,
    val total_tracks: Int,
    val available_markets: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val type: String,
    val uri: String,
)