package com.herokuapp.trytov.jarvis.features.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.herokuapp.trytov.jarvis.R
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

class LoginFragment : Fragment(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter
    private val mCallBackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFacebookLogin()
    }

    private fun initFacebookLogin() {
        val permissionNeeds = Arrays.asList("public_profile")

        btn_facebook.registerCallback(mCallBackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                presenter.loginByFacebook(result!!)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                Log.e("TESTT","eror: ${error.toString()}")
                presenter.loginError(error.toString())
            }
        })
        btn_facebook.setReadPermissions(permissionNeeds)
    }

    override fun showToast(messege: String) {
        Toast.makeText(activity, messege, Toast.LENGTH_LONG).show()
    }

    override fun resultLogin(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}