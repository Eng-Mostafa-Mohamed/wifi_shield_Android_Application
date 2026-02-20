package com.example.data.modules


data class IpReportResponseDto(

	val data: DataDto? = null
)

data class DataDto(

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
