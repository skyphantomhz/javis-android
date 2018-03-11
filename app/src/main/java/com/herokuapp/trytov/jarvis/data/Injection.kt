package com.herokuapp.trytov.jarvis.data

import android.content.Context
import com.herokuapp.trytov.jarvis.BuildConfig
import com.herokuapp.trytov.jarvis.data.repository.AuthenticateRepository
import com.herokuapp.trytov.jarvis.data.resource.AuthenticaDataSource
import com.herokuapp.trytov.jarvis.data.resource.PackageRequestDataSource
import com.herokuapp.trytov.jarvis.data.resource.local.PreferenceSource
import com.herokuapp.trytov.jarvis.data.resource.remote.PackageRequestRemoteSource
import com.herokuapp.trytov.jarvis.data.services.ApiClient
import com.herokuapp.trytov.jarvis.data.services.AuthenticateService
import com.herokuapp.trytov.jarvis.data.services.PackageRequestService

object Injection {
    private val packageRequestService = ApiClient.getInstance(BuildConfig.BASE_URL)
            .create(PackageRequestService::class.java)

    private val authenticaService = ApiClient.getInstance(BuildConfig.BASE_URL)
            .create(AuthenticateService::class.java)

    private fun provideRemotePackageRequestRepository() = PackageRequestRemoteSource.getInstance(packageRequestService)

    fun providePackageRequestRepository(): PackageRequestDataSource {
        return PackageRequestDataSource.getInstance(provideRemotePackageRequestRepository())
    }

    fun provideAuthenticaRepository(): AuthenticateRepository {
        return AuthenticaDataSource.getInstance(authenticaService)
    }

    fun providePreferenceRepository(context: Context) = PreferenceSource.getInstance(context)
}