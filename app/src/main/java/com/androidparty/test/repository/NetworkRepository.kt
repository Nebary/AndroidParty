package com.androidparty.test.repository

import com.androidparty.test.model.LoginRequest
import com.androidparty.test.model.ServerResponse
import com.androidparty.test.model.TokenResponse
import com.androidparty.test.remote.AndroidPartyApi
import com.androidparty.test.repository.provider.IDataProvider
import io.reactivex.Single
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val api: AndroidPartyApi) : IDataProvider {
    override fun authorization(loginRequest: LoginRequest): Single<TokenResponse> {
        return api.authorization(loginRequest)
    }

    override fun servers(token: String?): Single<List<ServerResponse>> {
        return api.servers(token)
    }
}