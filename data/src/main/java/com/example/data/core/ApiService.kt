package com.example.data.core

import com.example.data.modules.IpResponseDto
import com.example.data.modules.LoginRequest
import com.example.data.modules.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Ip_ApiService {

    @GET("?format=json")
    suspend fun getPublicIp(): IpResponseDto
}

interface RouterApiService {

    @POST("login")
    suspend fun routerLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
