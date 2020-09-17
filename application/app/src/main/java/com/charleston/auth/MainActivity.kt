package com.charleston.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.content_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), Observer<String> {

    private val tokenValid = "524156154d1a2we13154as4d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_first.setOnClickListener {
            execute()
        }
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: String) {
        log("emitting token $t")
    }

    override fun onError(e: Throwable) {
        log("error emit token ${e.message}")
    }

    override fun onComplete() {
    }

    private fun execute() {
        val requests = arrayListOf(
            createRequest(TokenRepository.token).compose(TokenObservableTransformer()),
            createRequest(TokenRepository.token).compose(TokenObservableTransformer()),
            createRequest(TokenRepository.token).compose(TokenObservableTransformer()),
            createRequest(TokenRepository.token).compose(TokenObservableTransformer()),
            createRequest(TokenRepository.invalidToken).compose(TokenObservableTransformer())
        )

        requests.forEach {
            it.subscribe(this)
        }
    }

    private fun createRequest(token: String): Observable<String> {
        return Observable.just(token)
            .delay(2, TimeUnit.SECONDS)
            .flatMap {
                if (it == tokenValid) {
                    Observable.just(it)
                } else {
                    requestError()
                }
            }
    }

    private fun requestError(): Observable<String> {
        return Observable.error<String>(Throwable("Invalid Token"))
    }

    private fun log(value: String) {
        Log.d(MainActivity::class.java.name, value)
    }
}