package com.example.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.* // استخدام Material 3 الموحد
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.OnboardingViewModel
import com.example.practice.ui.theme.dark_blue

// الألوان الموحدة للتطبيق بالكامل
val DeepBlue = Color(0xFF0F172A)
val ElectricBlue = Color(0xFF3B82F6)

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    navigateToLogin: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            com.example.practice.R.drawable.img3,
            "Secure Your Network",
            "Keep your WiFi safe from unauthorized devices and potential threats."
        ),
        OnboardingPage(
            com.example.practice.R.drawable.img1,
            "Monitor Connected Devices",
            "See all devices connected to your network in real-time and track usage."
        ),
        OnboardingPage(
            com.example.practice.R.drawable.img2,
            "Control Access Easily",
            "Block or allow devices instantly, giving you full control over your WiFi."
        )
    )

    val page = viewModel.currentPage
    val isLastPage = page == pages.lastIndex

    SetStatusBarColor(color = dark_blue,darkIcons = false)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (!isLastPage) {
                    TextButton(onClick = navigateToLogin) {
                        Text("Skip", color = Color.Gray, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Illustration Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = pages[page].image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.85f),
                    contentScale = ContentScale.Fit
                )
            }

            // 2. Text Content Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pages[page].title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = DeepBlue,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = pages[page].description,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            // 3. Bottom Controls Area
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Indicators
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = index == page
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(8.dp)
                                .width(if (isSelected) 24.dp else 8.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) ElectricBlue else Color.LightGray.copy(0.5f))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Navigation Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (page > 0) {
                        OutlinedButton(
                            onClick = { viewModel.previousPage() },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, Color.LightGray.copy(0.4f))
                        ) {
                            Text("Back", color = DeepBlue, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = {
                            if (isLastPage) viewModel.finishOnboarding(navigateToLogin)
                            else viewModel.nextPage()
                        },
                        modifier = Modifier
                            .weight(if (page > 0) 2.5f else 1f)
                            .height(56.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFC562FB),
                                        Color(0xFF8F59FD),
                                        Color(0xFF6236FF)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(0.dp)
                    )  {
                        Text(
                            text = if (isLastPage) "Get Started" else "Continue",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}