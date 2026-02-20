package com.example.data.repoImpl

import com.example.data.core.IpReportApiService
import com.example.data.mapper.toData
import com.example.domain.dataClasses.Data
import com.example.domain.repo.IpReportRepository

class IpReportRepositoryImpl(
    private val api: IpReportApiService
) : IpReportRepository {

    override suspend fun getIpData(ip: String): Data {
        return try {
            val response = api.getData(ipAddress = ip)
            response.data?.toData()
                ?: throw Exception("No Data Found")
        } catch (e: Exception) {
            throw Exception("Network Error: ${e.message}")
        }
    }

}
