package com.example.domain.dataClasses


data class DeviceResponseItem(

	val hostname: String? = null,

	val os: String? = null,

	val usageMb: Int? = null,

	val vendor: String? = null,

	val ip: String? = null,

	val deviceType: String? = null,

	val id: String? = null,

	val mac: String? = null,

	val status: String? = null,

	val username: String? = null
)

data class DeviceResponse(

	val deviceResponse: List<DeviceResponseItem?>? = null
)


data class BlockResponse(

    val message: String
)
