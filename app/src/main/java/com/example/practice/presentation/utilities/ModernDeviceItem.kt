package com.example.practice.presentation.utilities
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.dataClasses.DeviceResponseItem
import com.example.practice.R
import com.example.practice.ui.theme.AccentBlue
import com.example.practice.ui.theme.ErrorRed
import com.example.practice.ui.theme.SuccessGreen

@Composable
fun ModernDeviceItem(device: DeviceResponseItem,onBlockClick: (deviceId: String) -> Unit ) {
    val isBlocked = device.status?.lowercase() == "blocked"
    val statusColor = if (isBlocked) ErrorRed else SuccessGreen

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = (if (isBlocked) ErrorRed else AccentBlue).copy(alpha = 0.1f)
                ) {
                    val deviceIcon = when (device.deviceType?.lowercase()) {
                        "laptop", "pc", "computer" -> R.drawable.pc_ic
                        "mobile", "phone", "smartphone" -> R.drawable.mobile_ic
                        else -> R.drawable.pc_ic
                    }

                    Icon(
                        painter = painterResource(deviceIcon),
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp),
                        tint = if (isBlocked) ErrorRed else AccentBlue
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.hostname ?: "Unknown Host",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF1A1C1E)
                    )
                    Text(
                        text = device.ip ?: "0.0.0.0",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                StatusBadge(status = device.status ?: "Unknown", color = statusColor)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.4f))


            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoChip(Icons.Default.Refresh, "MAC", device.mac, Modifier.weight(1f))
                    InfoChip(Icons.Default.Settings, "OS", device.os, Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoChip(Icons.Default.Info, "Type", device.deviceType, Modifier.weight(1f))
                    InfoChip(Icons.Default.Build, "Usage", "${device.usageMb ?: 0} MB", Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))



           // block button
            Button(
                onClick = {
                    device.id?.let { onBlockClick(it) }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBlocked) SuccessGreen else ErrorRed
                )
            ) {
                Icon(if (isBlocked) Icons.Default.Check else Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isBlocked) "Unblock Device" else "Block Device", fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
fun InfoChip(icon: ImageVector, label: String, value: String?, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text(value ?: "-", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
        }
    }
}

@Composable
fun StatusBadge(status: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = CircleShape,
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Text(
            text = status.uppercase(),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = color
        )
    }
}


@Composable
fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = AccentBlue, strokeWidth = 3.dp)
    }
}

@Composable
fun ErrorState(msg: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Warning, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(48.dp))
        Text(msg, color = ErrorRed, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text("Try Again", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun EmptyState(text: String) {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        Icon(Icons.Default.Search, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(64.dp))
        Text(text, color = Color.Gray)
    }
}