package com.herokuapp.trytov.jarvis.data.resource.local

import android.content.Context
import android.content.SharedPreferences
import com.herokuapp.trytov.jarvis.data.model.Profile
import com.herokuapp.trytov.jarvis.data.repository.PreferenceRepository

class PreferenceSource : PreferenceRepository {
    override fun getProfile(): Profile {
        return Profile(sharedPreferences.getString(PREFERENCE_PROFILE, ""))
    }

    override fun setProfile(profile: Profile) {
        sharedPreferences.edit().putString(PREFERENCE_PROFILE, profile.id).apply()
    }

    companion object {

        private var INSTANCE: PreferenceRepository? = null
        private lateinit var sharedPreferences: SharedPreferences
        private const val PREFERENCE_NAME = "UserSharedPreference"
        private const val PREFERENCE_PROFILE = "AccessToken"

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