package com.androidparty.test

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.androidparty.test.di.AppComponent
import com.androidparty.test.di.DaggerAppComponent
import com.androidparty.test.di.NetworkModule


class AndroidPartyApp : Application() {

    lateinit var appComponent: AppComponent
    private var sharedPreferences: SharedPreferences? = null

    companion object {
      private  const val TOKEN_KEY = "token_key"
        var app: AndroidPartyApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.builder()
                .networkModule(NetworkModule(this))
                .build()
    }

    private fun getPreferences(): SharedPreferences? {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        }
        return sharedPreferences
    }

    fun getToken(): String? {
        return getPreferences()?.getString(TOKEN_KEY, "")
    }

    fun saveToken(token: String?) {
        val fullToken = "Bearer $token"
        getPreferences()?.edit()?.putString(TOKEN_KEY, fullToken)?.apply()
    }

    fun clearToken() {
        getPreferences()?.edit()?.clear()?.apply()
    }

}