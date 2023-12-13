package com.androidsquad.tunestream.services.model

data class UserProfile(
    val country: String,
    val display_name: String,
    val email: String,
    val href: String,
    val id: String,
    val images: List<Image>,
    val product: String,
    val type: String,
    val uri: String
)