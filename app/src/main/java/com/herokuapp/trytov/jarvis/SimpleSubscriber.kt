package com.herokuapp.trytov.jarvis

import com.herokuapp.trytov.jarvis.exception.RestApiException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class SimpleSubscriber<T> : Observer<T> {
    private var error: BaseException? = null
    private var value: T? = null

    override fun onSubscribe(d: Disposable) {

    }

    override fun onComplete() {
        if (error != null) {
            onCompleted(false, value, error)
            return
        }
        onCompleted(true, value, null)
    }

    open fun onCompleted(success: Boolean, value: T?, error: BaseException?) {

    }

    override fun onNext(value: T) {
        this.value = value
    }

    override fun onError(error: Throwable) {
        this.error = RestApiException(error)
        onComplete()
    }
}