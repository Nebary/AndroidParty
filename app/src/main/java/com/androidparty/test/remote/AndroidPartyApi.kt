package com.androidparty.test.remote


import com.androidparty.test.model.LoginRequest
import com.androidparty.test.model.ServerResponse
import com.androidparty.test.model.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AndroidPartyApi {

    @POST("tokens")
    fun authorization(@Body  loginRequest: LoginRequest): Single<TokenResponse>

    @GET("servers")
    fun servers(@Header("authorization") token: String?): Single<List<ServerResponse>>

}