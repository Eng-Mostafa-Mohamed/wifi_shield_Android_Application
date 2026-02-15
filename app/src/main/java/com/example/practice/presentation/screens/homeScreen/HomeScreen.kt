package com.example.practice.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.HomeViewModel
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.move
import com.example.practice.ui.theme.white_blue

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val ip by viewModel.ipState.collectAsState()

    SetStatusBarColor(color =dark_blue ,false)


    LaunchedEffect(Unit) {
      viewModel.fetchIp()
  }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            "Threat Intelligence Lookup",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
            , textAlign = TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth().border(
                width = 2.dp,
                color = white_blue,
                shape = RoundedCornerShape(20.dp)
            ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {


                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "You're connected to network ip : $ip",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }

    }
}
