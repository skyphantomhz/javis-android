package com.herokuapp.trytov.jarvis.features.home

import android.content.Context
import android.content.Intent
import com.herokuapp.trytov.jarvis.BasePresenter
import com.herokuapp.trytov.jarvis.BaseView
import com.herokuapp.trytov.jarvis.data.model.PackageResponse

interface HomeContract {
    interface View: BaseView<Presenter>{
        fun setTextInput(textInput: String)
        fun directToGooglePlayFullLink(appPackageName: String)
        fun directToGooglePlayShortLink(appPackageName: String)
        fun gotoSetting()
        fun getContextSource(): Context?
        fun showErrorByToast(messegeError: String)
        fun outputText(data: PackageResponse)
    }

    interface  Presenter: BasePresenter{

        fun getReqCodeSpeechInput(): Int
        fun resolveTextInput(requestCode: Int, resultCode: Int, data: Intent?)
        fun deviceNotsupport()
        fun directToGooglePlayDownloadAppSupport()
    }
}