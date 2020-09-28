package com.charleston.auth.retrofit.adapter

import io.reactivex.*
import retrofit2.HttpException

object RxAuthTransformer {

    fun <R> transformSingle(): SingleTransformer<R, Any> {
        return SingleTransformer { upstream -> upstream.map { it } }
    }

    fun <R> transformMaybe(): MaybeTransformer<R, Any> {
        return MaybeTransformer { upstream -> upstream.map { it } }
    }

    fun <R> transformObservable(): ObservableTransformer<R, Any> {
        return ObservableTransformer { upstream: Observable<*> ->
            upstream
                .map { it }
                .flatMap {
                    if (validateDateToken()) {
                        Observable.just(it)
                    } else {
                        requestAgain(upstream)
                    }
                }
                .onErrorResumeNext { e: Throwable ->
                    if (e is HttpException && e.code() == 401) {
                        return@onErrorResumeNext requestAgain(upstream)
                    } else {
                        return@onErrorResumeNext Observable.error(e)
                    }
                }
        }
    }

    fun transformCompletable(): CompletableTransformer {
        return CompletableTransformer { upstream -> upstream }
    }

    private fun requestNewToken(): Observable<String> {
        return Observable.just("521651d546as1dasfas")
    }

    private fun validateDateToken(): Boolean {
        return false
    }

    private fun requestAgain(upstream: Observable<*>): Observable<*> {
       return Observable.fromCallable {
            //cripto
           "s4ddas5451da5s24d1a"
        }
            .flatMap {
                requestNewToken()
                    .flatMap { novoToken ->
                        upstream
                    }
            }

    }
}