package com.charleston.auth

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class TokenObservableTransformer : ObservableTransformer<String, String> {

    override fun apply(upstream: Observable<String>): ObservableSource<String> {
        return upstream
            .onErrorResumeNext { e: Throwable ->
                log("Error ${e.message}")
                log("Renewing token")
                getNewToken()
            }
    }

    private fun log(value: String) {
        Log.d(MainActivity::class.java.name, value)
    }

    private fun getNewToken(): Observable<String> {
        return Observable.just(TokenRepository.token)
    }
}