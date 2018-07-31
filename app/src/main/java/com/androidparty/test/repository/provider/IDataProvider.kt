package com.androidparty.test.repository.provider


import com.androidparty.test.model.LoginRequest
import com.androidparty.test.model.ServerResponse
import com.androidparty.test.model.TokenResponse
import io.reactivex.Single

interface IDataProvider {

    fun authorization(loginRequest: LoginRequest): Single<TokenResponse>

    fun servers(token: String?): Single<List<ServerResponse>>
}