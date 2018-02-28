package com.herokuapp.trytov.jarvis.data.services

import com.herokuapp.trytov.jarvis.data.model.PackageRequest
import com.herokuapp.trytov.jarvis.data.model.PackageResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PackageRequestService {
    @GET("/")
    fun startServer(): Observable<String>

    @POST("/controlVoice")
    fun getResultAfterResolve(@Body()request : PackageRequest) : Observable<PackageResponse>
}