package com.herokuapp.trytov.jarvis.data.services

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson


object ApiClient {
    var retrofit: Retrofit? = null
    var okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
    var gson = GsonBuilder()
            .setLenient()
            .create()

    fun getInstance(baseUrl: String): Retrofit {
        return retrofit ?: Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().apply { retrofit = this }
    }
}
