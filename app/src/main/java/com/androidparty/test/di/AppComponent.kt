package com.androidparty.test.di


import android.content.Context
import com.androidparty.test.ui.login.LoginActivity
import com.androidparty.test.ui.servers.ServersActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class), (RepositoryModule::class)])
interface AppComponent {

    fun inject(loginActivity: LoginActivity)
    fun inject(serversActivity: ServersActivity)
    fun inject(context: Context)

}