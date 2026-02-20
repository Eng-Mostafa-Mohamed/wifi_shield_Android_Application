package com.example.domain.useCases

import com.example.domain.dataClasses.BlockResponse
import com.example.domain.dataClasses.DeviceResponseItem
import com.example.domain.repo.DevicesRepository

public class GetConnectedDevicesUseCase(private val repository: DevicesRepository){
    suspend operator fun invoke(): List<DeviceResponseItem>{
        return repository.getConnectedDevices()
    }
}

public class GetBlockedDevicesUseCase(private val repository: DevicesRepository){
    suspend operator fun invoke(): List<DeviceResponseItem>{
        return repository.getBlockedDevices()
    }
}

public class BlockDeviceUseCase(private val repository: DevicesRepository){
    suspend operator fun invoke(deviceId: String): BlockResponse {
        return repository.blockDevice(deviceId )
    }

}

public class UnBlockDeviceUseCase(private val repository: DevicesRepository){
    suspend operator fun invoke(deviceId: String): BlockResponse {
        return repository.unblockDevice(deviceId )
    }

}

public class DeleteDeviceUseCase(private val repository: DevicesRepository){
    suspend operator fun invoke(deviceId: String): BlockResponse {
        return repository.deleteDevice(deviceId )
    }

}