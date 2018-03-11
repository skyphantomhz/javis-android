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
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.herokuapp.trytov.jarvis.R
import com.herokuapp.trytov.jarvis.data.model.Profile
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), LoginContract.View {
    override lateinit var presenter: LoginContract.Presenter
    private var mCallBackManager: CallbackManager = CallbackManager.Factory.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFacebookLogin()
        initButtonLogin()
    }

    private fun initButtonLogin(){
        btn_login_facebook.setOnClickListener { btn_facebook.performClick() }
    }

    private fun initFacebookLogin() {
        btn_facebook.setReadPermissions("email")
        btn_facebook.registerCallback(mCallBackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val request = GraphRequest.newMeRequest(
                        result.getAccessToken()) { me, response ->
                    if (response.error != null) {
                        // handle error
                    } else {
                        val user_id = me.optString("id")
                        val user_email = me.optString("email")
                        presenter.listener.loginSuccess(Profile( user_id.toLong(),user_email.toString()))
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "last_name,first_name,email")
                request.parameters = parameters
                request.executeAsync()
            }
            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                Log.e("TESTT","eror: ${error.toString()}")
                presenter.loginError(error.toString())
            }
        })
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