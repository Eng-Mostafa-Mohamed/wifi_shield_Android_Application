package com.example.domain.dataClasses


data class IpReportResponse(

	val data: Data? = null
)

data class Data(

    val lastReportedAt: String?,
    val hostnames: List<String>?,
    val isWhitelisted: Boolean?,
    val abuseConfidenceScore: Int? = null,
    val numDistinctUsers: Int? = null,
    val isp: String? = null,
    val ipAddress: String? = null,
    val totalReports: Int? = null,
    val isTor: Boolean? = null,
    val ipVersion: Int? = null,
    val countryCode: String? = null,
    val domain: String? = null,
    val isPublic: Boolean? = null,
    val usageType: String? = null
)


data class IpReportUiState(
    val isLoading: Boolean = false,
    val data: Data? = null,
    val error: String? = null
)