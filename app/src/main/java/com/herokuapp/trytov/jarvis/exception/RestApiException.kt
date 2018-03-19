package com.herokuapp.trytov.jarvis.exception

import com.herokuapp.trytov.jarvis.BaseCallBack
import com.herokuapp.trytov.jarvis.BaseException
import com.herokuapp.trytov.jarvis.R
import com.herokuapp.trytov.jarvis.getStringResource
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

class RestApiException(override var error: Throwable) : BaseException {
    override var message: String? = null
    override fun onError(baseCallBack: BaseCallBack) {
        val callBack = baseCallBack as CallBackException
        error.let {
            when (it) {
                is HttpException -> resolveErrorHandledOnRestApi(it, callBack)
                is ConnectException -> callBack.connectException()
                is SocketTimeoutException ->  message = getStringResource(R.string.string_request_time_out)
                else -> message = getStringResource(R.string.string_exception_not_handle)
            }
        }
    }

    private fun resolveErrorHandledOnRestApi(error: HttpException, callBack: CallBackException) {
        message =when (error.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                callBack.unAuthorized()
                getStringResource(R.string.string_unauthorized)
            }
            HttpURLConnection.HTTP_FORBIDDEN -> {
                getStringResource(R.string.string_forbidden)
            }
            HttpURLConnection.HTTP_NOT_FOUND -> {
                getStringResource(R.string.string_not_found)
            }
            HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                getStringResource(R.string.string_internal_server_error)
            }
            HttpURLConnection.HTTP_UNAVAILABLE ->{
                getStringResource(R.string.string_account_incorrect)
            }
            else -> getStringResource(R.string.string_http_exception)
        }
    }

    interface CallBackException: BaseCallBack {
        fun connectException()
        fun unAuthorized()
    }
}