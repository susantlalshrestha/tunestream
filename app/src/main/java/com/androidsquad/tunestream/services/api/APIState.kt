package com.androidsquad.tunestream.services.api

sealed class APIState {
    object ProgressingState : APIState()
    data class DataState<D>(val data: D) : APIState()
    data class ErrorState(val error: String) : APIState()
    object FinishedState : APIState()
}