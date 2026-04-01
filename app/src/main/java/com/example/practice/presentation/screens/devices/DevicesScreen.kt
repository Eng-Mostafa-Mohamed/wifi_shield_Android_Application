package com.example.practice.presentation.screens.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.dataClasses.Device
import com.example.practice.R
import com.example.practice.presentation.viewmodels.DevicesViewModel

// الإعدادات البصرية المعتمدة
val PrimaryColor = Color(0xFF1C1B69)
val BackgroundScreen = Color(0xFFF4F5F7)
val StatusGreen = Color(0xFF4CAF50)

@Composable
fun DevicesScreen(
    viewModel: DevicesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.startScan()
    }
    val activeDevices = viewModel.devices.filter { !it.status.contains("BLOCKED") }

    Scaffold(
        containerColor = BackgroundScreen,
        topBar = {
            Surface(
                shadowElevation = 8.dp,
                color = PrimaryColor,
                modifier = Modifier.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Text(
                        text = "Wifi Shield",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (viewModel.isScanning) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(12.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(StatusGreen))
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = viewModel.statusMessage,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.startScan() },
                containerColor = PrimaryColor,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                icon = { Icon(Icons.Default.Refresh, null) },
                text = { Text("Scan Network", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        if (activeDevices.isEmpty() && !viewModel.isScanning) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No active devices found",
                        color = PrimaryColor.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(activeDevices) { index, device ->
                    DeviceItem(
                        index = index + 1,
                        device = device,
                        onBlockClick = { viewModel.toggleDeviceBlock(device.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun DeviceItem(index: Int, device: Device, onBlockClick: () -> Unit) {
    val isOnline = device.status.contains("ONLINE")

    val deviceIcon = when {
        device.name.lowercase().contains("desktop") || device.name.lowercase().contains("pc") -> R.drawable.pc_ic
        device.name.lowercase().contains("unknown") || device.name == "--" -> R.drawable.help_outline_ic
        else -> R.drawable.mobile_ic
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = PrimaryColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("#$index", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryColor)
                    }
                }

                Spacer(Modifier.width(12.dp))

                Icon(
                    painter = painterResource(id = deviceIcon),
                    contentDescription = null,
                    tint = PrimaryColor,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.name,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = PrimaryColor
                    )
                    Text(
                        text = device.mac,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = if (isOnline) "Active" else "Offline",
                    color = if (isOnline) StatusGreen else Color.Gray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = (if (isOnline) StatusGreen else Color.Gray).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onBlockClick,
                modifier = Modifier.fillMaxWidth().height(45.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Restrict Device",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}