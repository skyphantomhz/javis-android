package com.herokuapp.trytov.jarvis.data.resource

import com.herokuapp.trytov.jarvis.data.model.AuthenticateResponse
import com.herokuapp.trytov.jarvis.data.model.Profile
import com.herokuapp.trytov.jarvis.data.repository.AuthenticateRepository
import com.herokuapp.trytov.jarvis.data.resource.remote.PackageRequestRemoteSource
import com.herokuapp.trytov.jarvis.data.services.AuthenticateService
import com.herokuapp.trytov.jarvis.data.services.PackageRequestService
import com.herokuapp.trytov.jarvis.util.RxUtils
import io.reactivex.Observable

class AuthenticaDataSource(val authenticateService: AuthenticateService) : AuthenticateRepository {
    override fun getToken(user: Profile): Observable<AuthenticateResponse> = authenticateService.geToken(user).compose(RxUtils.applySchedulers())

    companion object {
        private var INSTANCE: AuthenticateRepository? = null
        fun getInstance(authenticateService: AuthenticateService): AuthenticateRepository {
            return INSTANCE ?: AuthenticaDataSource(authenticateService).apply { INSTANCE = this }
        }
    }
}