package com.example.domain.dataClasses

data class PingResult(
    val averageLatency: Long,
    val minLatency: Long,
    val maxLatency: Long,
    val status: PingStatus
)

enum class PingStatus {
    EXCELLENT, GOOD, SLOW, BAD, FAILED
}
