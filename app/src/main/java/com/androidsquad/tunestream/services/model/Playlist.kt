package com.androidsquad.tunestream.services.model

data class Playlist(
    val collaborative: Boolean,
    val description: String,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: UserProfile,
    val public: Boolean,
    val snapshot_id: String,
    val type: String,
    val uri: String
)
