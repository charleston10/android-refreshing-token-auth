package com.charleston.auth.retrofit

import com.charleston.auth.retrofit.adapter.RxAuthAdapter
import retrofit2.Retrofit

class HttpClient {

    fun <T> create(clazz: Class<T>) = client().create(clazz::class.java)

    private fun client(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxAuthAdapter.create())
            .baseUrl("https://api.github.com/")
            .build()
    }
}