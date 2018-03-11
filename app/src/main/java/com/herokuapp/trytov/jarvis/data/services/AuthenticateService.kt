package com.herokuapp.trytov.jarvis.data.services

import com.herokuapp.trytov.jarvis.data.model.AuthenticateResponse
import com.herokuapp.trytov.jarvis.data.model.Profile
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticateService {
    @POST("/authenticate")
    fun geToken(@Body()user : Profile) : Observable<AuthenticateResponse>
}