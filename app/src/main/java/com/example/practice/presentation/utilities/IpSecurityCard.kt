package com.example.practice.presentation.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.dataClasses.Data
import com.example.practice.ui.theme.bg_gray

@Composable
fun IpSecurityCard(
    data: Data?,
    isLoading: Boolean,
    error: String?
) {

    val riskScore = data?.abuseConfidenceScore ?: 0

    val riskColor = when {
        riskScore == 0 -> Color(0xFF22C55E)
        riskScore in 1..50 -> Color(0xFFF59E0B)
        else -> Color(0xFFEF4444)
    }

    val riskText = when {
        riskScore == 0 -> "Safe"
        riskScore in 1..50 -> "Suspicious"
        else -> "Dangerous"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(16.dp, RoundedCornerShape(24.dp))
            .background(Color.White, RoundedCornerShape(24.dp))
            .border(1.dp, bg_gray, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = riskColor)
                }
            }

            error != null -> {
                Text(
                    text = error,
                    color = Color.Red
                )
            }

            data != null -> {

                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "IP Security Status",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = riskText,
                                color = riskColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(riskColor.copy(alpha = 0.15f), CircleShape)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "$riskScore%",
                                color = riskColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Divider()

                    InfoRow("IP Address", data.ipAddress ?: "-")
                    InfoRow("Country", data.countryCode ?: "-")
                    InfoRow("ISP", data.isp ?: "-")
                    InfoRow("Usage Type", data.usageType ?: "-")
                    InfoRow("Reports", data.totalReports?.toString() ?: "0")
                    InfoRow(
                        "Tor Network",
                        if (data.isTor == true) "Yes ⚠" else "No"
                    )
                }
            }
        }
    }
}
