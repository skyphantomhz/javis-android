package com.herokuapp.trytov.jarvis.data.resource.remote

import com.herokuapp.trytov.jarvis.data.model.PackageRequest
import com.herokuapp.trytov.jarvis.data.model.PackageResponse
import com.herokuapp.trytov.jarvis.data.repository.PackageRequestRepository
import com.herokuapp.trytov.jarvis.data.services.PackageRequestService
import com.herokuapp.trytov.jarvis.util.RxUtils
import io.reactivex.Observable

class PackageRequestRemoteSource(val packageRequestService: PackageRequestService): PackageRequestRepository {
    override fun startServer() = packageRequestService.startServer().compose(RxUtils.applySchedulers())

    override fun getResultAfterResolve(data: PackageRequest): Observable<PackageResponse> {
        return packageRequestService.getResultAfterResolve(data).compose(RxUtils.applySchedulers())
    }

    companion object {
        private var INSTANCE: PackageRequestRemoteSource? = null
        fun getInstance(packageRequestService: PackageRequestService): PackageRequestRemoteSource
                = INSTANCE ?: PackageRequestRemoteSource(packageRequestService).apply { INSTANCE = this }
    }
}
