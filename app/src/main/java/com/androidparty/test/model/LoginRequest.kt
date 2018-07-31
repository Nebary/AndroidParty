package com.androidparty.test.model

import com.google.gson.annotations.SerializedName

class LoginRequest(
        @SerializedName("username")
        val userName: String,
        val password: String
)