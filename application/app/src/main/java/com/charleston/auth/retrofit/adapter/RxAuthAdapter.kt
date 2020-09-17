package com.charleston.auth.retrofit.adapter

import com.charleston.auth.R
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

class RxAuthAdapter : CallAdapter.Factory() {

    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    companion object {
        fun create(): CallAdapter.Factory {
            return RxAuthAdapter()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        original.get(
            returnType, annotations, retrofit
        )

        return RxAuthWrapper(wrapped = original as CallAdapter<R, *>)
    }
}