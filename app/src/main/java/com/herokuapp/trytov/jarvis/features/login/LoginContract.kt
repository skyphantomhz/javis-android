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
        var listener: LoginPresenter.LoginCallBack
        fun loginError(message: String)
        fun resultLogin(requestCode: Int, resultCode: Int, data: Intent?)
    }
}