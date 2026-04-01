package com.example.data.repoImpl

import android.util.Log
import com.example.domain.dataClasses.AlertModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SecurityRepository {
    private var mSocket: Socket? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _alertFlow = MutableSharedFlow<AlertModel>(replay = 1)
    val alertFlow = _alertFlow.asSharedFlow()

    init {
        try {
            val serverUrl = "http://192.168.1.2:9090/"
            mSocket = IO.socket(serverUrl)

            mSocket?.on(Socket.EVENT_CONNECT) {
                Log.d("SOCKET_DEBUG", " Connected to Server: $serverUrl")
            }

            mSocket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                Log.e("SOCKET_DEBUG", " Connection Error: ${args[0]}")
            }

            mSocket?.connect()
            setupSocketListeners()
        } catch (e: Exception) {
            Log.e("SOCKET_DEBUG", " Init Error: ${e.message}")
        }
    }

    private fun setupSocketListeners() {
        mSocket?.on("push_notification") { args ->
            Log.d("SOCKET_DEBUG", " Signal Received from Server!")
            try {
                val data = args[0] as JSONObject
                val alert = AlertModel(
                    title = data.getString("title"),
                    message = data.getString("message"),
                    device = data.optString("device", "Hacker Device"),
                    confidence = data.optString("confidence", "99%"),
                    time = data.optString("time", "Now")
                )

                scope.launch {
                    _alertFlow.emit(alert)
                }
            } catch (e: Exception) {
                Log.e("SOCKET_DEBUG", " Data Parsing Error: ${e.message}")
            }
        }
    }

    fun disconnect() {
        mSocket?.disconnect()
    }
}