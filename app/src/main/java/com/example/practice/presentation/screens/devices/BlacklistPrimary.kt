package com.example.practice.presentation.screens.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
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

val BlacklistPrimary = Color(0xFF1C1B69)
val BlacklistBackground = Color(0xFFF4F5F7)
val StatusGreenColor = Color(0xFF4CAF50)
val StatusRedColor = Color(0xFFD32F2F)

@Composable
fun BlacklistScreen(
    viewModel: DevicesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.startScan()
    }
    val blockedDevices = viewModel.devices.filter {
        it.status.uppercase().contains("BLOCKED")
    }

    Scaffold(
        containerColor = BlacklistBackground,
        topBar = {
            Surface(
                shadowElevation = 8.dp,
                color = BlacklistPrimary,
                modifier = Modifier.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Blacklist",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(Modifier.height(8.dp))

                    if (viewModel.isScanning) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth().height(2.dp),
                            color = Color.White,
                            trackColor = Color.White.copy(alpha = 0.2f)
                        )
                    } else {
                        Text(
                            text = "Restricted devices from your network",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.startScan() },
                containerColor = BlacklistPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                icon = { Icon(Icons.Default.Refresh, null) },
                text = { Text("Refresh List", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        if (blockedDevices.isEmpty() && !viewModel.isScanning) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = BlacklistPrimary.copy(alpha = 0.1f)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "No blocked devices found",
                        color = BlacklistPrimary.copy(alpha = 0.4f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 80.dp), // زيادة الـ bottom عشان الزرار م يغطيش اخر جهاز
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(blockedDevices) { index, device ->
                    BlockedDeviceItem(
                        index = index + 1,
                        device = device,
                        onUnblockClick = { viewModel.toggleDeviceBlock(device.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BlockedDeviceItem(index: Int, device: Device, onUnblockClick: () -> Unit) {
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
                    color = StatusRedColor.copy(alpha = 0.1f),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("#$index", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = StatusRedColor)
                    }
                }

                Spacer(Modifier.width(12.dp))

                Icon(
                    painter = painterResource(id = deviceIcon),
                    contentDescription = null,
                    tint = StatusRedColor,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.name,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = BlacklistPrimary
                    )
                    Text(
                        text = "MAC: ${device.mac}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "Blocked",
                    color = StatusRedColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .background(
                            color = StatusRedColor.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onUnblockClick,
                modifier = Modifier.fillMaxWidth().height(45.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlacklistPrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Allow Access",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}