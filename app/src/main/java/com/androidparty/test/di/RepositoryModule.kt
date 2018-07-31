package com.androidparty.test.di

import com.androidparty.test.remote.AndroidPartyApi
import com.androidparty.test.repository.NetworkRepository
import com.androidparty.test.repository.provider.IDataProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(api: AndroidPartyApi): IDataProvider {
        return NetworkRepository(api)
    }
}