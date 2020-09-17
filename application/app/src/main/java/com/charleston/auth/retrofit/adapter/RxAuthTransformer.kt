package com.charleston.auth.retrofit.adapter

import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

object RxAuthTransformer {

    fun <R> transformSingle(): SingleTransformer<R, Any> {
        return SingleTransformer { upstream -> upstream.map { it } }
    }

    fun <R> transformMaybe(): MaybeTransformer<R, Any> {
        return MaybeTransformer { upstream -> upstream.map { it } }
    }

    fun <R> transformObservable(): ObservableTransformer<R, Any> {
        return ObservableTransformer { upstream -> upstream.map { it } }
    }

    fun transformCompletable(): CompletableTransformer {
        return CompletableTransformer { upstream -> upstream }
    }
}