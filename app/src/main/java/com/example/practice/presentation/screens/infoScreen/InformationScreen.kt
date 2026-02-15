package com.example.practice.presentation.screens.infoScreen

import SecurityTipCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.dark_blue


@Composable
fun AboutScreen() {
    SetStatusBarColor(color =dark_blue ,false)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(blue, Color(0xFF9C27B0))
                    )
                )
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    painter = painterResource(R.drawable.security_ic),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Secure Your Network",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Smart tips to keep your connection safe",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        SecurityTipCard(
            icon = R.drawable.lock_ic,
            title = "Use WPA2 or WPA3 Encryption",
            description = "Always use WPA2 or WPA3 encryption on your Wi-Fi network to prevent unauthorized access. Avoid using outdated protocols like WEP, which are easy to hack. Regularly update your router’s firmware to patch security vulnerabilities. Consider setting a strong, unique password with a combination of letters, numbers, and symbols. Enable network firewalls and monitor connected devices for suspicious activity."
        )

        SecurityTipCard(
            icon = R.drawable.router_ic,
            title = "Change Router Password",
            description = "Change your router’s default password immediately after setup, as default credentials are widely known and easily exploited. Use a strong, unique password that includes uppercase, lowercase, numbers, and symbols. Update passwords periodically, and avoid sharing them publicly. If your router supports it, create a separate guest network with its own password to isolate visitors from your main devices. Always log out of your router’s admin panel when not in use."
        )

        SecurityTipCard(
            icon = R.drawable.visibility_off,
            title = "Disable WPS",
            description = "Turn off WPS (Wi-Fi Protected Setup) because it can expose your network to attacks such as brute-force PIN cracking. Instead, manually connect new devices using a strong Wi-Fi password. If your router requires WPS for legacy devices, consider updating those devices or using a secure alternative. Regularly check your router settings for unauthorized WPS activity and firmware updates to strengthen network security."
        )

        SecurityTipCard(
            icon = R.drawable.wifi_off,
            title = "Hide SSID",
            description = "Hiding your SSID can prevent casual users from seeing your network name, adding an extra layer of privacy. Combine SSID hiding with strong encryption (WPA2/WPA3) and a unique password. Avoid using easily guessable network names that reveal personal information. Monitor connected devices regularly, disable remote administration unless necessary, and consider using a VPN to encrypt traffic on your Wi-Fi network. For added security, segment your network into separate VLANs for IoT, work, and personal devices."
        )


        Spacer(modifier = Modifier.height(20.dp))
    }
}

