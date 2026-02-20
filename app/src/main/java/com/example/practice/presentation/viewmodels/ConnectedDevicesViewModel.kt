package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataClasses.DeviceResponseItem
import com.example.domain.useCases.BlockDeviceUseCase
import com.example.domain.useCases.GetConnectedDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConnectedDevicesViewModel @Inject constructor(
    private val getConnectedDevicesUseCase: GetConnectedDevicesUseCase,
    private val blockDeviceUseCase: BlockDeviceUseCase
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
                val result = getConnectedDevicesUseCase()
                _devices.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun blockDevice(deviceId: String){
        viewModelScope.launch {
            _errorMessage.value = null
            try {
                blockDeviceUseCase(deviceId)
                getDevices()
            } catch (e: Exception){
                _errorMessage.value = e.message ?: "Failed to block device"
            }
        }
    }
}
