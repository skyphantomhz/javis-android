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
import com.herokuapp.trytov.jarvis.data.repository.PreferenceRepository
import com.herokuapp.trytov.jarvis.exception.RestApiException

class HomePresenter(val sharePreferenceRepository: PreferenceRepository, val view: HomeContract.View, val listener: HomeCallBack) : HomeContract.Presenter {
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

    private fun getTextInput(data: String) {
        view.setTextInput(data)
//        Thread.sleep(2000)
//        getResultAfterResolve(data)
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

    override fun sendResultAfterResolveVoice(text: String) {
        val observer = object : SimpleSubscriber<PackageResponse>() {
            override fun onCompleted(success: Boolean, value: PackageResponse?, error: BaseException?) {
                super.onCompleted(success, value, error)
                when {
                    success && value != null -> getDataSuccess(value)
                    else -> handleExceptionOnRestApi(error!! as RestApiException)
                }
            }
        }
        val token = sharePreferenceRepository.getToken()
        Injection.providePackageRequestRepository().getResultAfterResolve(PackageRequest(token, text)).subscribe(observer)
    }

    private fun handleExceptionOnRestApi(error: RestApiException) {

        error.onError(object : RestApiException.CallBackException {
            override fun unAuthorized() {
                listener.unAuthorized()
            }

            override fun connectException() {
                listener.detectInternetState()
            }
        })
        when {
            !error.message.isNullOrEmpty() -> view.showErrorByToast(error.message!!)
            else -> false
        }

    }

    private fun getDataSuccess(data: PackageResponse) {
        view.outputText(data)
    }

    interface HomeCallBack {
        fun detectInternetState()
        fun unAuthorized()
    }
}