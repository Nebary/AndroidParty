package com.androidparty.test.di

import android.content.Context
import com.androidparty.test.Environment
import com.androidparty.test.remote.AndroidPartyApi
import com.androidparty.test.remote.OkHttpClientHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule constructor(val context: Context) {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    @Singleton
    @Provides
    fun providesNetworkService(gson: Gson): AndroidPartyApi {
        return Retrofit.Builder()
                .baseUrl(Environment.SERVICE_ENDPOINT)
                .client(OkHttpClientHelper(context).createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(AndroidPartyApi::class.java)
    }
}
