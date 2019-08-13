package com.mercari.data.networking.base


import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mercari.data.BuildConfig
import com.mercari.data.networking.manager.AuthorizationManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.io.IOException
import java.util.concurrent.TimeUnit

abstract class AbstractService<S> {
    var authorizationManager: AuthorizationManager
    internal var service: S
    private var serviceType: Class<S>? = null
    private val TIME_OUT_MIL_SECS = BuildConfig.SERVICE_TIME_OUT_MILSEC
    private var context: Context? = null

    constructor(authorizationManager: AuthorizationManager,
        serviceType: Class<S>
    ) {
        this.serviceType = serviceType
        this.authorizationManager = authorizationManager
        this.context = context
        service = createService(BuildConfig.API_BASE_URL)
    }


    fun setBaseUrl(baseUrl: String) {
        service = createService(baseUrl)
    }


    private fun createService(baseUrl: String): S {


        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofitBuilder = Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))

        val client = createOkHttpClient()
        return retrofitBuilder.client(client).build().create(serviceType!!)
    }


    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val originalRequest = chain.request()

            // Nothing to add to intercepted request if:
            // a) Authorization value is empty because user is not logged in yet
            // b) There is already a header with updated Authorization value

            if (TextUtils.isEmpty(authorizationManager.getAccessToken()) || this@AbstractService.alreadyHasAuthorizationHeader(
                    originalRequest
                )
            ) {
                return@Interceptor chain.proceed(originalRequest)
            }

            // Request customization: add request headers
            val requestBuilder =
                originalRequest.newBuilder().header(AUTHORIZATION_KEY, authorizationManager.getAccessToken())
                    .addHeader("content-type", "application/json")
                    .addHeader("X-PlatformEntity", "Android")
                    .addHeader("X-Version", BuildConfig.VERSION_NAME)
                    .addHeader("X-BuildNo", "" + BuildConfig.VERSION_CODE)
                    .addHeader("X-Language", "")
                    .method(originalRequest.method(), originalRequest.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        })
        return httpClient.connectTimeout(TIME_OUT_MIL_SECS.toLong(), TimeUnit.MILLISECONDS).build()
    }

    private fun alreadyHasAuthorizationHeader(request: Request): Boolean {
        return !TextUtils.isEmpty(request.header(AUTHORIZATION_KEY))
    }

    companion object {

        private val AUTHORIZATION_KEY = "Authorization"
    }
}
