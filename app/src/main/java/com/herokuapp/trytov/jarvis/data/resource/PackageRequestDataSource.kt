package com.herokuapp.trytov.jarvis.data.resource

import com.herokuapp.trytov.jarvis.data.model.PackageRequest
import com.herokuapp.trytov.jarvis.data.model.PackageResponse
import com.herokuapp.trytov.jarvis.data.repository.PackageRequestRepository
import com.herokuapp.trytov.jarvis.data.resource.remote.PackageRequestRemoteSource
import io.reactivex.Observable


class PackageRequestDataSource(val packageRequestRemoteSource: PackageRequestRemoteSource): PackageRequestRepository {
    override fun startServer() = packageRequestRemoteSource.startServer()

    override fun getResultAfterResolve(data: PackageRequest): Observable<PackageResponse> {
        return packageRequestRemoteSource.getResultAfterResolve(data)
    }

    companion object {
        private var INSTANCE: PackageRequestDataSource? = null
        fun getInstance(packageRequestRemoteSource: PackageRequestRemoteSource): PackageRequestDataSource {
            return INSTANCE?: PackageRequestDataSource(packageRequestRemoteSource).apply { INSTANCE = this }
        }
    }

}