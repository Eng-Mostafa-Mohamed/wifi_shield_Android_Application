package com.example.domain.useCases

import com.example.domain.dataClasses.Data
import com.example.domain.repo.IpReportRepository

class GetIpReportUseCase (private val reportRepository: IpReportRepository){
    suspend operator fun invoke(ip: String): Data {
        return reportRepository.getIpData(ip)
    }
}