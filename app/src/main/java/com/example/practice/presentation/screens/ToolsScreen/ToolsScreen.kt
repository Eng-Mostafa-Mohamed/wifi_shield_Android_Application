package com.example.practice.presentation.screens.ToolsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.dataClasses.PingStatus
import com.example.e_commerce.utils.MainButton
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.ToolsViewModel
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue

@Composable
fun ToolsScreen(viewModel: ToolsViewModel = hiltViewModel()) {
    SetStatusBarColor(color =dark_blue ,false)

    val ping by viewModel.ping.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Tools",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Blue,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth().height(150.dp)
                .padding(horizontal = 20.dp)
                .border(
                    width = 2.dp,
                    color = blue,
                    shape = RoundedCornerShape(20.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Ping Test",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))


                if (ping == null) {
                    Text("Avg Latency: ")
                    Text("Min: ")
                    Text("Status: ")
                } else if (ping!!.status == PingStatus.FAILED) {
                    Text("Connection failed ")
                } else {
                    Text("Avg Latency: ${ping!!.averageLatency} ms")
                    Text("Min: ${ping!!.minLatency} ms, Max: ${ping!!.maxLatency} ms")
                    Text("Status: ${ping!!.status}")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // space between card and button
        MainButton(text ="Run Ping" , contentColor = Color.White,bgBrush = blueBrush, onClick = {viewModel.runPingTest()})

    }
}
