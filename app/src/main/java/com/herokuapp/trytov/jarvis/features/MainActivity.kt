package com.herokuapp.trytov.jarvis.features

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.facebook.FacebookSdk
import com.herokuapp.trytov.jarvis.BaseException
import com.herokuapp.trytov.jarvis.R
import com.herokuapp.trytov.jarvis.SimpleSubscriber
import com.herokuapp.trytov.jarvis.data.Injection
import com.herokuapp.trytov.jarvis.data.model.Profile
import com.herokuapp.trytov.jarvis.exception.RestApiException
import com.herokuapp.trytov.jarvis.extensions.isInternetAvailable
import com.herokuapp.trytov.jarvis.extensions.showDialogOnlyAccept
import com.herokuapp.trytov.jarvis.features.home.HomeContract
import com.herokuapp.trytov.jarvis.features.home.HomeFragment
import com.herokuapp.trytov.jarvis.features.home.HomePresenter
import com.herokuapp.trytov.jarvis.features.login.LoginContract
import com.herokuapp.trytov.jarvis.features.login.LoginFragment
import com.herokuapp.trytov.jarvis.features.login.LoginPresenter
import com.herokuapp.trytov.jarvis.util.TextReader
import com.herokuapp.trytov.jarvis.util.replaceFragmentInActivity


class MainActivity : AppCompatActivity(), HomePresenter.HomeCallBack, LoginPresenter.LoginCallBack {
    private lateinit var homePresenter: HomeContract.Presenter
    private lateinit var loginPresenter: LoginContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getResultAfterResolve()
        initSpeaker()
        initView()
        TextReader.initSpeaker(applicationContext)
        TextReader.getInstance()
    }

    fun initView(){
        if(Injection.providePreferenceRepository(applicationContext).getProfile().id.isNotEmpty()){
            directToHomePage()
        }else{
            directToLoginPage()
        }
    }

    override fun loginSuccess(profile: Profile) {
        Injection.providePreferenceRepository(applicationContext).setProfile(profile)
        directToHomePage()
    }

    override fun detectInternetState() {
        if (!this.isInternetAvailable()) {
            this.showDialogOnlyAccept(this, resources.getString(R.string.string_title_no_internet), resources.getString(R.string.string_content_no_internet))
        }
    }

    private fun directToHomePage() {
        HomeFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.frame_layout_context)
            homePresenter = HomePresenter(it, this@MainActivity)
        }
    }

    private fun directToLoginPage() {
        LoginFragment.newInstance().let {
            replaceFragmentInActivity(it, R.id.frame_layout_context)
            loginPresenter = LoginPresenter(it, this@MainActivity)
        }
    }

    private fun initSpeaker() {
        TextReader.initSpeaker(applicationContext)
        TextReader.getInstance()
    }

    private fun getResultAfterResolve() {
        val observer = object : SimpleSubscriber<String>() {
            override fun onCompleted(success: Boolean, value: String?, error: BaseException?) {
                super.onCompleted(success, value, error)
                when {
                    success && value != null -> showToast(value)
                    else -> handleExceptionOnRestApi(error!! as RestApiException)
                }
            }
        }
        Injection.providePackageRequestRepository().startServer().subscribe(observer)
    }

    private fun handleExceptionOnRestApi(error: RestApiException) {
        error.callBack = object : RestApiException.CallBackError {
            override fun connectException() {
                detectInternetState()
            }
        }
        error.onError(this)
        when {
            !error.message.isNullOrEmpty() -> showToast(error.message!!)
            else -> false
        }
    }

    fun showToast(messege: String) {
        Toast.makeText(this, messege, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FacebookSdk.getCallbackRequestCodeOffset()){
            loginPresenter.resultLogin(requestCode, resultCode, data)
        }
    }
}
