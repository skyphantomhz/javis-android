package com.herokuapp.trytov.jarvis.data.repository

import com.herokuapp.trytov.jarvis.data.model.PackageRequest
import com.herokuapp.trytov.jarvis.data.model.PackageResponse
import io.reactivex.Observable

interface PackageRequestRepository {
    fun startServer(): Observable<String>
    fun getResultAfterResolve(data: PackageRequest): Observable<PackageResponse>
}