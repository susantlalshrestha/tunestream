package com.androidsquad.tunestream.services.api

interface APIObservable<S : APIState> {
    fun subscribe(subscriber: (state:S) -> Unit)
}