package com.herokuapp.trytov.jarvis.features.home

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import com.herokuapp.trytov.jarvis.BaseException
import com.herokuapp.trytov.jarvis.SimpleSubscriber
import com.herokuapp.trytov.jarvis.data.Injection
import com.herokuapp.trytov.jarvis.data.model.PackageRequest
import com.herokuapp.trytov.jarvis.data.model.PackageResponse
import com.herokuapp.trytov.jarvis.exception.RestApiException

class HomePresenter(val view: HomeContract.View, val listener: HomeCallBack) : HomeContract.Presenter {
    init {
        view.presenter = this
    }

    val REQ_CODE_SPEECH_INPUT = 100

    override fun start() {
    }

    override fun getReqCodeSpeechInput() = REQ_CODE_SPEECH_INPUT

    override fun resolveTextInput(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    getTextInput(result[0])
                }
            }
        }
    }

    private fun getTextInput(data: String){
        view.setTextInput(data)
        getResultAfterResolve(data)
    }

    override fun deviceNotsupport() {
        view.gotoSetting()
    }

    override fun directToGooglePlayDownloadAppSupport() {
        val appPackageName = "com.google.android.googlequicksearchbox"
        try {
            view.directToGooglePlayShortLink(appPackageName)
        } catch (anfe: ActivityNotFoundException) {
            view.directToGooglePlayFullLink(appPackageName)
        }
    }

    private fun getResultAfterResolve(text: String) {
        val observer = object : SimpleSubscriber<PackageResponse>() {
            override fun onCompleted(success: Boolean, value: PackageResponse?, error: BaseException?) {
                super.onCompleted(success, value, error)
                when {
                    success && value != null -> getDataSuccess(value)
                    else -> handleExceptionOnRestApi(error!! as RestApiException)
                }
            }
        }
        Injection.providePackageRequestRepository().getResultAfterResolve(PackageRequest(text)).subscribe(observer)
    }

    private fun handleExceptionOnRestApi(error: RestApiException) {
        error.callBack = object : RestApiException.CallBackError {
            override fun connectException() {
                listener.detectInternetState()
            }
        }
        view.getContextSource()?.let {
            error.onError(it)
            when {
                !error.message.isNullOrEmpty() -> view.showErrorByToast(error.message!!)
                else -> false
            }
        } ?: run{
            view.showErrorByToast("ko co context")
        }
    }

    private fun getDataSuccess(data: PackageResponse) {
        view.outputText(data)
    }

    interface HomeCallBack{
        fun detectInternetState()
    }
}