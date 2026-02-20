package com.example.practice.presentation.screens.ToolsScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.dataClasses.PingStatus
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.ToolsViewModel
import com.example.practice.ui.theme.*

@Composable
fun ToolsScreen(viewModel: ToolsViewModel = hiltViewModel()) {
    val ping by viewModel.ping.collectAsState()

    // Local state to manage the 'Testing' UI phase
    var isTesting by remember { mutableStateOf(false) }

    // 1. ANIMATION: Pulsing/Breathing effect
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isTesting) 1.12f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "scale"
    )

    // Set Status Bar to match Auth/Hub style
    SetStatusBarColor(color = Color.White, darkIcons = true)

    // Automatically stop ripple animation when ping result returns
    LaunchedEffect(ping) {
        if (ping != null) {
            isTesting = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ---
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Ping Diagnostic",
                fontFamily = titleFont,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = dark_blue
            )
            Text(
                text = if (isTesting) "Analyzing network path..." else "Tap to test latency",
                color = Color.Gray,
                fontSize = 15.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.weight(0.6f))

        // --- 2. THE ANIMATED SPHERE ---
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {

            // SONAR RIPPLES: Visualizing the "Ping" signal
            if (isTesting) {
                repeat(3) { i ->
                    val rippleScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 2.8f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2200, delayMillis = i * 600),
                            repeatMode = RepeatMode.Restart
                        ), label = "ripple"
                    )
                    Box(
                        Modifier
                            .size(160.dp)
                            .scale(rippleScale)
                            .background(blue.copy(alpha = 1f - (rippleScale / 2.8f)), CircleShape)
                    )
                }
            }

            // MAIN SPHERE: The Interactive Image
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .shadow(
                        elevation = 25.dp,
                        shape = CircleShape,
                        spotColor = blue,
                        ambientColor = blue
                    )
                    .background(blueBrush, CircleShape)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (!isTesting) {
                            isTesting = true
                            viewModel.runPingTest()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.speed_ic),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(85.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.4f))

        // --- 3. THE FLOATING RESULT CARD ---
        // Hidden during testing, shown when result is SUCCESS/FAILED
        AnimatedVisibility(
            visible = ping != null && !isTesting,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            ping?.let { data ->
                ResultDisplayCard(data)
            }
        }
    }
}

@Composable
fun ResultDisplayCard(data: com.example.domain.dataClasses.PingResult) { // Adjusted based on your likely package
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg_gray.copy(alpha = 0.7f), RoundedCornerShape(32.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data.status == PingStatus.FAILED) {
            Icon(
                painter = painterResource(id = R.drawable.blocked_ic),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text("Connection Timeout", color = Color.Red, fontWeight = FontWeight.Bold)
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ResultColumn(label = "AVG", value = "${data.averageLatency}ms", color = move)
                // Divider
                Box(Modifier.width(1.dp).height(30.dp).background(Color.LightGray).align(Alignment.CenterVertically))
                ResultColumn(label = "MIN", value = "${data.minLatency}ms", color = Color.DarkGray)
                // Divider
                Box(Modifier.width(1.dp).height(30.dp).background(Color.LightGray).align(Alignment.CenterVertically))
                ResultColumn(label = "MAX", value = "${data.maxLatency}ms", color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Connection Status Badge
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Stable Connection",
                    color = Color(0xFF10B981), // Emerald green
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun ResultColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 22.sp, color = color, fontWeight = FontWeight.ExtraBold, fontFamily = titleFont)
    }
}