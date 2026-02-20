package com.example.data.mapper

import com.example.data.modules.DataDto
import com.example.domain.dataClasses.Data



fun DataDto.toData(): Data {
    return Data(
        lastReportedAt = this.lastReportedAt,
        abuseConfidenceScore = this.abuseConfidenceScore,
        numDistinctUsers = this.numDistinctUsers,
        isp = this.isp,
        ipAddress = this.ipAddress,
        totalReports = this.totalReports,
        hostnames = this.hostnames,
        isWhitelisted = this.isWhitelisted,
        isTor = this.isTor,
        ipVersion = this.ipVersion,
        countryCode = this.countryCode,
        domain = this.domain,
        isPublic = this.isPublic,
        usageType = this.usageType
    )
}


fun Data.toData(): DataDto {
    return DataDto(
        lastReportedAt = this.lastReportedAt,
        abuseConfidenceScore = this.abuseConfidenceScore,
        numDistinctUsers = this.numDistinctUsers,
        isp = this.isp,
        ipAddress = this.ipAddress,
        totalReports = this.totalReports,
        hostnames = this.hostnames,
        isWhitelisted = this.isWhitelisted,
        isTor = this.isTor,
        ipVersion = this.ipVersion,
        countryCode = this.countryCode,
        domain = this.domain,
        isPublic = this.isPublic,
        usageType = this.usageType
    )
}