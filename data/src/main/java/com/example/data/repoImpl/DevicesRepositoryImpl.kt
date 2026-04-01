package com.example.data.repoImpl

import com.example.data.core.RouterApiService
import com.example.data.core.modules.SessionManager
import com.example.data.mapper.toDeviceEntity
import com.example.domain.dataClasses.BlockResponse
import com.example.domain.dataClasses.DeviceResponseItem
import com.example.domain.repo.DevicesRepository

class DevicesRepositoryImpl(
    private val apiService: RouterApiService
  ): DevicesRepository {




    override suspend fun getConnectedDevices(): List<DeviceResponseItem> {
       return try {
          val token= SessionManager.authToken
           val response = apiService.getConnectedDevices("Bearer $token")

           if (response.isSuccessful) {

               val body = response.body() ?: emptyList()

               return body.map { dto ->
                   dto.toDeviceEntity()
               }

           } else {
               throw Exception("Failed to load devices")
           }

       }catch (e: Exception) {
           throw Exception(e.message ?: "Unknown error")
       }

    }

    override suspend fun getBlockedDevices(): List<DeviceResponseItem> {
        return try {
            val token= SessionManager.authToken
            val response = apiService.getBlockedDevices("Bearer $token")

            if (response.isSuccessful) {

                val body = response.body() ?: emptyList()

                return body.map { dto ->
                    dto.toDeviceEntity()
                }

            } else {
                throw Exception("Failed to load devices")
            }

        }catch (e: Exception) {
            throw Exception(e.message ?: "Unknown error")
        }
    }

    override suspend fun blockDevice(deviceId: String): BlockResponse {
        return try {
            val token= SessionManager.authToken
            apiService.blockDevice(deviceId,"Bearer $token")
      }catch (e: Exception) {
            throw e
        }
    }


    override suspend fun unblockDevice(deviceId: String): BlockResponse {
        return try {
            val token= SessionManager.authToken
            apiService.unblockDevice(deviceId,"Bearer $token")
        }catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteDevice(deviceId: String): BlockResponse {
        return try {
            val token= SessionManager.authToken
            apiService.deleteDevice(deviceId,"Bearer $token")
        }catch (e: Exception) {
            throw e
        }
    }
}



