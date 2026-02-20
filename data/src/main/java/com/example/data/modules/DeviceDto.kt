package com.example.data.modules

import com.example.domain.dataClasses.DeviceResponseItem

data class DeviceDto(

    val hostname: String? = null,
    val os: String? = null,
    val usage_mb: Int? = null,
    val vendor: String? = null,
    val ip: String? = null,
    val device_type: String? = null,
    val id: String? = null,
    val mac: String? = null,
    val status: String? = null,
    val username: String? = null
)

