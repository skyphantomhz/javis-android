package com.herokuapp.trytov.jarvis.features.login

import android.content.Intent
import android.util.Log
import com.facebook.login.LoginResult
import com.herokuapp.trytov.jarvis.data.model.Profile


class LoginPresenter(val view: LoginContract.View, val listener: LoginCallBack) : LoginContract.Presenter {

    init {
        view.presenter = this
    }
    override fun loginError(message: String) {
        Log.e("TESTT","eror: message")
        view.showToast(message)
    }

    override fun loginByFacebook(result: LoginResult) {
        Log.e("TESTT","success")
        view.showToast(result.accessToken.userId)
        listener.loginSuccess(Profile(result.accessToken.userId))
    }

    override fun resultLogin(requestCode: Int, resultCode: Int, data: Intent?) {
        view.resultLogin(requestCode, resultCode, data)
    }

    override fun start() {

    }

    interface LoginCallBack {
        fun loginSuccess(profile: Profile)
    }
}