package com.example.practice.presentation.screens.devices

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.practice.R
import com.example.practice.presentation.utilities.ModernDeviceItem
import com.example.practice.presentation.viewmodels.ConnectedDevicesViewModel
import com.example.practice.ui.theme.AccentBlue
import com.example.practice.ui.theme.ErrorRed
import com.example.practice.ui.theme.SurfaceGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevicesScreen(
    navController: NavController,
    viewModel: ConnectedDevicesViewModel = hiltViewModel()
) {
    val devices by viewModel.devices.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) { viewModel.getDevices() }

    Scaffold(



        // number of connected devices
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Network Devices",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold)
                        )

                        Surface(
                            color = AccentBlue.copy(alpha = 0.1f),
                            shape = CircleShape
                        ) {
                            Text(
                                text = "${devices.size} Online",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentBlue
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = SurfaceGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {




            Spacer(modifier = Modifier.height(8.dp))




            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(devices) { device ->
                    ModernDeviceItem(device,onBlockClick = { deviceId ->
                        viewModel.blockDevice(deviceId)
                    })
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }





        }
    }
}


