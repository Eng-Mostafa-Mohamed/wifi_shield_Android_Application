package com.example.practice.presentation.viewmodels

import android.util.Log // مهم جداً للـ Logs
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataClasses.Device
import com.example.domain.repo.DeviceSocketRepository
import com.example.domain.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val socketRepo: DeviceSocketRepository,
    private val authRepo: UserRepository
) : ViewModel() {

    private val TAG = "SOCKET_DEBUG"

    var devices by mutableStateOf<List<Device>>(emptyList())
        private set

    var isScanning by mutableStateOf(false)
        private set

    var statusMessage by mutableStateOf("Ready to scan your network")
        private set

    init {
        val serverUrl = "http://192.168.1.2:5000"
        Log.d(TAG, "--- Init ViewModel: Attempting to connect to $serverUrl ---")

        socketRepo.connect(serverUrl)

        socketRepo.observeScanResults { list ->
            Log.d(TAG, "SUCCESS: Received ${list.size} devices from server")
            viewModelScope.launch(Dispatchers.Main) {
                devices = list
                isScanning = false
                statusMessage = "Scan Complete: ${list.size} devices found"
            }
        }

        socketRepo.observeStatus { msg ->
            Log.d(TAG, "STATUS UPDATE: $msg")
            viewModelScope.launch(Dispatchers.Main) {
                statusMessage = msg
            }
        }
    }

    fun startScan() {
        Log.d(TAG, "Button Clicked: startScan() called")

        val uid = authRepo.getCurrentUserId()
        Log.d(TAG, "User Identifier (Email): $uid")

        if (uid != null) {
            isScanning = true
            devices = emptyList()
            Log.d(TAG, "Emitting 'scan_devices' event for user: $uid")
            socketRepo.scanDevices(uid)
        } else {
            Log.e(TAG, "ERROR: User is null, cannot scan")
            statusMessage = "Error: User not authenticated"
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel Cleared: Disconnecting socket")
        socketRepo.disconnect()
    }

    fun toggleDeviceBlock(deviceId: Int) {
        val uid = authRepo.getCurrentUserId()
        Log.d(TAG, "Attempting to toggle block for device ID: $deviceId, User: $uid")

        if (uid != null) {
            statusMessage = "Processing request for device $deviceId..."
            socketRepo.toggleBlock(uid, deviceId)
        } else {
            Log.e(TAG, "ERROR: User is null, cannot toggle block")
            statusMessage = "Error: Authentication failed"
        }
    }

    init {

        socketRepo.observeBlockDone { deviceId, action ->
            Log.d(TAG, "BLOCK SUCCESS: Device $deviceId is now $action")
            viewModelScope.launch(Dispatchers.Main) {
                statusMessage = "Device $deviceId updated: $action"
                // ممكن هنا تعيد عمل Scan تلقائي عشان تحدث القائمة
                startScan()
            }
        }
    }
}