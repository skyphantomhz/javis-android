package com.herokuapp.trytov.jarvis.data.services

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.*

object UnsafeOkHttpClient {
    val unsafeOkHttpClient: OkHttpClient
        get() {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf<X509Certificate>()

                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                })
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.getSocketFactory()

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String, session: SSLSession): Boolean {
                        return true
                    }
                })

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
}