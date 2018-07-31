package com.androidparty.test.remote

import android.content.Context
import com.androidparty.test.AndroidPartyApp
import com.androidparty.test.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientHelper(context: Context) {

    init {
        (context.applicationContext as AndroidPartyApp)
                .appComponent
                .inject(context)
    }

    fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor).build()
    }

    companion object {
        private const val CONNECTION_TIMEOUT = 20L
        private const val WRITE_TIMEOUT = 20L
        private const val READ_TIMEOUT = 20L
    }
}
