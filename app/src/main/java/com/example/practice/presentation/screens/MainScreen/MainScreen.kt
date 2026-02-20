package com.example.practice.presentation.screens.MainScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.utilities.IpSecurityCard
import com.example.practice.presentation.utilities.ServiceCardPremium
import com.example.practice.presentation.viewmodels.MainViewModel
import com.example.practice.ui.theme.*
import com.example.practice.R


@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val ip by viewModel.ipState.collectAsState()
    val report by viewModel.reportState.collectAsState()

    SetStatusBarColor(color = Color.White, darkIcons = true)

    LaunchedEffect(Unit) {
        viewModel.fetchIp()
    }

    Scaffold(
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(top = 32.dp, bottom = 32.dp)
        ) {


            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Network Hub",
                            fontSize = 32.sp,
                            fontFamily = titleFont,
                            fontWeight = FontWeight.Bold,
                            color = dark_blue
                        )
                        Text(
                            text = "System secure and active",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(bg_gray, CircleShape)
                            .clickable { navController.navigate("wifiSettings") },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.wifi_off),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                               ,colorFilter = ColorFilter.tint(primaryColor)
                        )
                    }
                }
            }


            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .shadow(20.dp, RoundedCornerShape(28.dp), ambientColor = move, spotColor = move)
                        .background(
                            brush = blueBrush,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .clip(RoundedCornerShape(28.dp))
                ) {

                    Canvas(modifier = Modifier.size(200.dp).offset(x = 180.dp, y = (-50).dp)) {
                        drawCircle(color = Color.White.copy(alpha = 0.1f))
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).background(Color(0xFF4CAF50), CircleShape))
                            Spacer(Modifier.width(8.dp))
                            Text("ACTIVE CONNECTION", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Column {
                            Text(text = "Home_WiFi_5G", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                            Text(text = "Public IP: $ip", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        }
                    }
                }
            }



            item {
                Text(
                    text = "Services",
                    fontFamily = titleFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = dark_blue,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        ServiceCardPremium(
                            title = "Devices",
                            icon = R.drawable.devices_ic,
                            modifier = Modifier.weight(1f),
                            color = Color(0xFF6366f1)
                        ) { navController.navigate("connectedDevices") }

                        ServiceCardPremium(
                            title = "Ping Test",
                            icon = R.drawable.speed_ic,
                            modifier = Modifier.weight(1f),
                            color = Color(0xFFf59e0b)
                        ) { navController.navigate("tools") }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        ServiceCardPremium(
                            title = "Blocked",
                            icon = R.drawable.blocked_ic,
                            modifier = Modifier.weight(1f),
                            color = Color(0xFFef4444)
                        ) { navController.navigate("blockedDevices") }

                        ServiceCardPremium(
                            title = "Guide",
                            icon = R.drawable.guide_ic,
                            modifier = Modifier.weight(1f),
                            color = Color(0xFF10b981)
                        ) { navController.navigate("Guide") }
                    }
                }
            }

            item {
                IpSecurityCard(
                    data = report.data,
                    isLoading = report.isLoading,
                    error = report.error
                )
            }

        }
    }
}

