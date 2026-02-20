package com.example.domain.repo

import com.example.domain.dataClasses.Data

interface IpReportRepository {

    suspend fun getIpData(ip: String): Data
}