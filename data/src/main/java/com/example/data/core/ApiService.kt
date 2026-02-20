package com.example.data.core

import com.example.data.modules.DeviceDto
import com.example.data.modules.IpReportResponseDto
import com.example.data.modules.IpResponseDto
import com.example.data.modules.LoginRequest
import com.example.data.modules.LoginResponse
import com.example.domain.dataClasses.BlockResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface IpReportApiService {

    @GET("check")
    suspend fun getData(
        @Header("Key") key: String="2dad44b1bb44b2f96c5314e946c5227e1c76a317443726324b4d6effc49ae982aa6ee9d210da5c48",
        @Header("Accept") accept: String = "application/json",
        @Query("ipAddress") ipAddress: String,
        @Query("maxAgeInDays") maxAgeInDays: Int = 90
    ): IpReportResponseDto
}





interface RouterApiService {

    @POST("login")
    suspend fun routerLogin(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("devices")
    suspend fun getConnectedDevices(
        @Header("Authorization") token: String
    ): Response<List<DeviceDto>>


    @GET("blacklist")
    suspend fun getBlockedDevices(
        @Header("Authorization") token: String
    ): Response<List<DeviceDto>>

    @POST("block/{deviceId}")
    suspend fun blockDevice(
        @Path("deviceId") deviceId: String,
        @Header("Authorization") token: String
    ): BlockResponse

    @POST("unblock/{deviceId}")
    suspend fun unblockDevice(
        @Path("deviceId") deviceId: String,
        @Header("Authorization") token: String
    ): BlockResponse

    @POST("delete/{deviceId}")
    suspend fun deleteDevice(
        @Path("deviceId") deviceId: String,
        @Header("Authorization") token: String
    ): BlockResponse

}







interface Ip_ApiService {

    @GET("?format=json")
    suspend fun getPublicIp(): IpResponseDto
}



