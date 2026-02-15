package com.example.practice.presentation.screens.devices

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerce.utils.MainButton
import com.example.practice.SetStatusBarColor
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import kotlinx.coroutines.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import org.json.JSONArray
import org.json.JSONObject

data class DeviceInfo(
    val ip: String,
    val mac: String,
    val deviceType: String,
    val hostname: String,
    val vendor: String,
    val os: String
)

@Composable
fun DevicesScreen() {

    SetStatusBarColor(color =dark_blue ,false)


    var devices by remember { mutableStateOf<List<DeviceInfo>>(emptyList()) }
    var scanning by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    suspend fun fetchDevicesFromApi(): List<DeviceInfo> = coroutineScope {
        val list = mutableListOf<DeviceInfo>()
        try {
            val apiUrl = "http://192.168.1.2:5000/" // ضع هنا IP جهازك أو السيرفر
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 3000
            connection.readTimeout = 3000

            val responseCode = connection.responseCode
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val device = DeviceInfo(
                        ip = obj.optString("ip", "Unknown"),
                        mac = obj.optString("mac", "Unknown"),
                        deviceType = obj.optString("device_type", "Unknown"),
                        hostname = obj.optString("hostname", "Unknown"),
                        vendor = obj.optString("vendor", "Unknown"),
                        os = obj.optString("os", "Unknown")
                    )
                    list.add(device)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Connected Devices",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Text("Devices Found: ${devices.size}")

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(devices) { device ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .border(2.dp, blue, RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("IP: ${device.ip}")
                        Text("Hostname: ${device.hostname}")
                        Text("MAC: ${device.mac}")
                        Text("Vendor: ${device.vendor}")
                        Text("OS: ${device.os}")
                        Text("Device Type: ${device.deviceType}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MainButton(
            text = "Fetch Devices",bgBrush = blueBrush,
            onClick = {
                if (!scanning) {
                    scanning = true
                    scope.launch {
                        devices = fetchDevicesFromApi()
                        scanning = false
                        Log.d("Devices", devices.toString())
                    }
                }
            }
        )
    }
}
