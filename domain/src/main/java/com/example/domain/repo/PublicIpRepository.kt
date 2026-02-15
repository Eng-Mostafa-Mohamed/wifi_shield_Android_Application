package com.example.domain.repo

import com.example.domain.dataClasses.IpResponse

interface PublicIpRepository {
    suspend fun getPublicIp(): IpResponse
}