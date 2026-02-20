package com.example.domain.repo

import com.example.domain.dataClasses.BlockResponse
import com.example.domain.dataClasses.DeviceResponseItem

interface DevicesRepository {

    suspend fun getConnectedDevices(): List<DeviceResponseItem>
    suspend fun getBlockedDevices(): List<DeviceResponseItem>


    suspend fun blockDevice(deviceId: String): BlockResponse

    suspend fun unblockDevice(deviceId: String): BlockResponse

    suspend fun deleteDevice(deviceId: String): BlockResponse

}