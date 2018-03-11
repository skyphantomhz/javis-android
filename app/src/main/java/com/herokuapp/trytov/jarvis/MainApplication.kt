package com.herokuapp.trytov.jarvis

import android.app.Application

open class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: MainApplication
            private set
    }
}

