package com.herokuapp.trytov.jarvis

import android.content.Context

interface BaseException {
    var error: Throwable
    var message: String?
    fun onError(baseCallBack: BaseCallBack)
}

interface BaseCallBack{}