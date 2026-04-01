package com.example.domain.repo

import com.example.domain.dataClasses.Device

interface DeviceSocketRepository {
    fun connect(url: String)
    fun scanDevices(userId: String)
    fun toggleBlock(userId: String, deviceId: Int)
    fun observeScanResults(onResult: (List<Device>) -> Unit)
    fun observeStatus(onStatus: (String) -> Unit)
    fun disconnect()
    fun observeBlockDone(onDone: (Int, String) -> Unit)
}