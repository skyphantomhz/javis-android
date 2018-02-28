package com.herokuapp.trytov.jarvis.exception

import android.content.Context
import com.herokuapp.trytov.jarvis.BaseException
import com.herokuapp.trytov.jarvis.R
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection

class RestApiException(override var error: Throwable) : BaseException {
    override var message: String? = null
    lateinit var callBack: CallBackError
    override fun onError(context: Context) {
        error.let {
            when (it) {
                is HttpException -> resolveErrorHandledOnRestApi(it, context)
                is ConnectException -> callBack.connectException()
                else -> message = context.resources.getString(R.string.string_exception_not_handle)
            }
        }
    }

    private fun resolveErrorHandledOnRestApi(error: HttpException, context: Context) {
        message =when (error.code()) {
            HttpURLConnection.HTTP_FORBIDDEN -> {
                context.resources.getString(R.string.string_forbidden)
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                context.resources.getString(R.string.string_not_found)
            }
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                context.resources.getString(R.string.string_internal_server_error)
            }
            else -> context.resources.getString(R.string.string_http_exception)
        }
    }

    interface CallBackError {
        fun connectException()
    }
}