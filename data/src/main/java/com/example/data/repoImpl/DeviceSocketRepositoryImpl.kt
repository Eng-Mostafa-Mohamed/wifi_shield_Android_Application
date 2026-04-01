package com.example.data.repoImpl

import com.example.domain.dataClasses.Device
import com.example.domain.repo.DeviceSocketRepository
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject

class DeviceSocketRepositoryImpl @Inject constructor() : DeviceSocketRepository {
    private var socket: Socket? = null

    override fun connect(url: String) {
        socket?.disconnect()
        socket = IO.socket(url).connect()
    }

    override fun scanDevices(userId: String) {
        val data = JSONObject().put("user_id", userId)
        socket?.emit("scan_devices", data)
    }

    override fun toggleBlock(userId: String, deviceId: Int) {
        val data = JSONObject()
            .put("user_id", userId)
            .put("device_id", deviceId)
        socket?.emit("toggle_block", data)
    }

    override fun observeScanResults(onResult: (List<Device>) -> Unit) {
        socket?.on("scan_results") { args ->
            val data = args[0] as JSONObject
            val array = data.getJSONArray("devices")
            val list = mutableListOf<Device>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(Device(
                    id = obj.getInt("id"),
                    name = obj.getString("name"),
                    mac = obj.getString("mac"),
                    status = obj.getString("status")
                ))
            }
            onResult(list)
        }
    }

    override fun observeStatus(onStatus: (String) -> Unit) {
        socket?.on("status_update") { args ->
            val data = args[0] as JSONObject
            onStatus(data.getString("msg"))
        }
    }

    override fun observeBlockDone(onDone: (Int, String) -> Unit) {
        socket?.on("block_done") { args ->
            val data = args[0] as JSONObject
            val id = data.getInt("id")
            val action = data.getString("action")
            onDone(id, action)
        }
    }

    override fun disconnect() {
        socket?.disconnect()
        socket = null
    }
}