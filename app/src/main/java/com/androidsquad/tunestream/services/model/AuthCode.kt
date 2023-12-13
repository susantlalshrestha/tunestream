package com.androidsquad.tunestream.services.model

data class AuthCode(
    val code: String,
    val state: String?,
)