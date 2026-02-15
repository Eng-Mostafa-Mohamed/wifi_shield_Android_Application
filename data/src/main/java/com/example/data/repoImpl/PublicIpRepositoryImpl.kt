package com.example.data.repoImpl

import com.example.data.core.Ip_ApiService
import com.example.data.mapper.toIpResponse
import com.example.domain.dataClasses.IpResponse
import com.example.domain.repo.PublicIpRepository

class PublicIpRepositoryImpl(private val api : Ip_ApiService): PublicIpRepository {
    override suspend fun getPublicIp(): IpResponse {
        val response =api.getPublicIp()
        return response.toIpResponse()
    }


}