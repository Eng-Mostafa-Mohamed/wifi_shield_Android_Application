package com.example.data.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: Ip_ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.ipify.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Ip_ApiService::class.java)
    }

}

object RetrofitInstanceForRouter {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val router_api: RouterApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://MostafaMohamed11.pythonanywhere.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RouterApiService::class.java)
    }

}



object RetrofitInstanceForIpReport{

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val IpReportApi: IpReportApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.abuseipdb.com/api/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IpReportApiService::class.java)
    }

}