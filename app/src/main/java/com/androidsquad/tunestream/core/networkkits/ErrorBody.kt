package com.androidsquad.tunestream.core.networkkits

data class ErrorBody(
    val error: Error
)

data class Error(
    val status: Int,
    val message: String
)