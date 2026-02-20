package com.example.practice.presentation.screens.GuideScreen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.ui.theme.*

@Composable
fun GuideScreen() {


    SetStatusBarColor(color = dark_blue, false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(blueBrush) // Using your blueBrush from theme
                .padding(top = 40.dp, bottom = 40.dp, start = 24.dp, end = 24.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.security_ic),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Security Guide",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontFamily = titleFont,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Expert tips to bulletproof your WiFi",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 15.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }


        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Essential Steps",
                fontFamily = titleFont,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = dark_blue
            )

            // Modern Security Tips
            SecurityTipCardPremium(
                icon = R.drawable.lock_ic,
                title = "WPA3 Encryption",
                description = "Always use WPA2 or WPA3 encryption on your Wi-Fi network to prevent unauthorized access. Avoid using outdated protocols like WEP, which are easy to hack. Regularly update your router’s firmware to patch security vulnerabilities."
            )

            SecurityTipCardPremium(
                icon = R.drawable.router_ic,
                title = "Admin Credentials",
                description = "Change your router’s default password immediately. Use a strong, unique password that includes uppercase, lowercase, numbers, and symbols. Update passwords periodically, and avoid sharing them publicly."
            )

            SecurityTipCardPremium(
                icon = R.drawable.visibility_off,
                title = "WPS Security",
                description = "Turn off WPS (Wi-Fi Protected Setup) because it can expose your network to attacks such as brute-force PIN cracking. Instead, manually connect new devices using a strong Wi-Fi password."
            )

            SecurityTipCardPremium(
                icon = R.drawable.wifi_off,
                title = "SSID Management",
                description = "Hiding your SSID can prevent casual users from seeing your network name. Combine SSID hiding with strong encryption and segment your network into separate VLANs for IoT devices."
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun SecurityTipCardPremium(
    icon: Int,
    title: String,
    description: String
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(bg_gray.copy(alpha = 0.5f))
            .clickable { isExpanded = !isExpanded }
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = move,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Title
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = dark_blue,
                fontFamily = titleFont
            )

            // Arrow
            Icon(
                painter = if (isExpanded) painterResource(id = R.drawable.expand_less_116842) else
                    painterResource(id = R.drawable.expand_more_119176),
                contentDescription = null,
                tint = Color.Gray
            )
        }


        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )
            }
        }
    }
}