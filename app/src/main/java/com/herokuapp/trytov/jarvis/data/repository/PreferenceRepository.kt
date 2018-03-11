package com.herokuapp.trytov.jarvis.data.repository

import com.herokuapp.trytov.jarvis.data.model.Profile

interface PreferenceRepository {
    fun getToken(): String
    fun setToken(token: String)
    fun tokenIsEmpty(): Boolean
}