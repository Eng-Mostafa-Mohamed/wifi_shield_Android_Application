package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataClasses.DeviceResponseItem
import com.example.domain.useCases.BlockDeviceUseCase
import com.example.domain.useCases.DeleteDeviceUseCase
import com.example.domain.useCases.GetBlockedDevicesUseCase
import com.example.domain.useCases.GetConnectedDevicesUseCase
import com.example.domain.useCases.UnBlockDeviceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BlockedDevicesViewModel @Inject constructor(
    private val getBlockedDevicesUseCase: GetBlockedDevicesUseCase,
    private val unBlockDeviceUseCase: UnBlockDeviceUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase
) : ViewModel() {

    private val _devices = MutableStateFlow<List<DeviceResponseItem>>(emptyList())
    val devices: StateFlow<List<DeviceResponseItem>> = _devices

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getDevices() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val result = getBlockedDevicesUseCase()
                _devices.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun unblockDevice(deviceId: String){
        viewModelScope.launch {
            _errorMessage.value = null
            try {
                unBlockDeviceUseCase(deviceId)
                getDevices()
            } catch (e: Exception){
                _errorMessage.value = e.message ?: "Failed to block device"
            }
        }
    }
    fun deleteDevice(deviceId: String){
        viewModelScope.launch {
            _errorMessage.value = null
            try {
                deleteDevice(deviceId)
                getDevices()
            } catch (e: Exception){
                _errorMessage.value = e.message ?: "Failed to block device"
            }
        }
    }
}
