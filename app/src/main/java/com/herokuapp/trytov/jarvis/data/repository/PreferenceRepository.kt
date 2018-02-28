package com.herokuapp.trytov.jarvis.data.repository

import com.herokuapp.trytov.jarvis.data.model.Profile

interface PreferenceRepository {
    fun getProfile(): Profile
    fun setProfile(profile: Profile)
}