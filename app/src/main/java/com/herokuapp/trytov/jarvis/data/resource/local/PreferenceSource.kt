package com.herokuapp.trytov.jarvis.data.resource.local

import android.content.Context
import android.content.SharedPreferences
import com.herokuapp.trytov.jarvis.data.model.Profile
import com.herokuapp.trytov.jarvis.data.repository.PreferenceRepository

class PreferenceSource : PreferenceRepository {
    override fun tokenIsEmpty(): Boolean {
        val token = sharedPreferences.getString(PREFERENCE_TOKEN, "")
        return when{
            token == "" -> true
            else -> false
        }
    }

    override fun getToken() = sharedPreferences.getString(PREFERENCE_TOKEN, "")
    override fun setToken(token: String) {
        sharedPreferences.edit().putString(PREFERENCE_TOKEN, token).apply()
    }
    companion object {

        private var INSTANCE: PreferenceRepository? = null
        private lateinit var sharedPreferences: SharedPreferences
        private const val PREFERENCE_NAME = "UserSharedPreference"
        private const val PREFERENCE_TOKEN = "AccessToken"

        fun getInstance(context: Context): PreferenceRepository {
            return INSTANCE ?: PreferenceSource()
                    .apply {
                        INSTANCE = this
                        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                    }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}