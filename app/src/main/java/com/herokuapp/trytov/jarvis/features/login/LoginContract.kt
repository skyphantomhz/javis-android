package com.herokuapp.trytov.jarvis.features.login

import android.content.Intent
import com.facebook.login.LoginResult
import com.herokuapp.trytov.jarvis.BasePresenter
import com.herokuapp.trytov.jarvis.BaseView

interface LoginContract{
    interface View: BaseView<Presenter>{
        fun showToast(messege: String)
        fun resultLogin(requestCode: Int, resultCode: Int, data: Intent?)
    }
    interface Presenter: BasePresenter{
        fun loginByFacebook(result: LoginResult)
        fun loginError(toString: String)
        fun resultLogin(requestCode: Int, resultCode: Int, data: Intent?)

    }
}