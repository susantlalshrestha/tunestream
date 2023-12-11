package com.androidsquad.tunestream.services.model

data class AuthToken (
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val refresh_token: String
)