package com.herokuapp.trytov.jarvis.data.repository

import com.herokuapp.trytov.jarvis.data.model.AuthenticateResponse
import com.herokuapp.trytov.jarvis.data.model.Profile
import io.reactivex.Observable

interface AuthenticateRepository {
    fun getToken(user: Profile): Observable<AuthenticateResponse>
}