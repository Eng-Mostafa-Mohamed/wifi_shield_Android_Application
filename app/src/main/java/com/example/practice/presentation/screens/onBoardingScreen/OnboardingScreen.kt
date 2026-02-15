package com.example.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.OnboardingViewModel
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.move
import com.example.practice.ui.theme.primaryColor
import com.example.practice.ui.theme.progressBgColor
import com.example.practice.ui.theme.secondaryColor
import com.example.practice.ui.theme.whiteText


// Data class for onboarding page
data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
)

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
    val progress = (page + 1) / pages.size.toFloat()



    SetStatusBarColor(color =dark_blue ,false)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(60.dp))


            Image(
                painter = painterResource(id = pages[page].image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(7f)
                    .fillMaxHeight(0.4f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = pages[page].title,
                style = MaterialTheme.typography.h5,
                color = dark_blue, fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = pages[page].description,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))





            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = if (page < pages.lastIndex) secondaryColor else primaryColor,
                backgroundColor = progressBgColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (page > 0) {
                    Button(
                        onClick = { viewModel.previousPage() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (page < pages.lastIndex) secondaryColor else primaryColor
                        )
                    ) {
                        Text("Back", color = whiteText)
                    }
                } else {
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Button(
                    onClick = {
                        if (page < pages.lastIndex)
                            viewModel.nextPage()
                        else
                            viewModel.finishOnboarding(navigateToLogin)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (page < pages.lastIndex) secondaryColor else primaryColor
                    )
                ) {
                    Text(
                        text = if (page < pages.lastIndex) "Next" else "Get Started",
                        color = whiteText
                    )
                }
            }



        }
    }

}
