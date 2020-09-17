package com.charleston.auth.retrofit.adapter

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class RxAuthWrapper<R>(
    private val wrapped: CallAdapter<R, *>
) : CallAdapter<R, Any> {

    override fun responseType(): Type = wrapped.responseType()

    override fun adapt(call: Call<R>): Any {
        return when (val result = wrapped.adapt(call)) {
            is Single<*> -> result.compose(RxAuthTransformer.transformSingle())
            is Maybe<*> -> result.compose(RxAuthTransformer.transformMaybe())
            is Observable<*> -> result.compose(RxAuthTransformer.transformObservable())
            is Completable -> result.compose(RxAuthTransformer.transformCompletable())
            else -> result
        }
    }
}