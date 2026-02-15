package com.example.practice.presentation.screens.WelcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import com.example.e_commerce.utils.MainButton
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.WelcomeViewModel
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.secondaryColor


@Composable
fun WelcomeScreen(navController: NavController,modifier: Modifier = Modifier,viewModel: WelcomeViewModel= hiltViewModel()) {
    val userName by viewModel.username
    SetStatusBarColor(color =secondaryColor ,false)

    Box(modifier= Modifier
        .fillMaxSize()
        .background(secondaryColor)){


        Column(
           modifier= Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
           ){

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.6f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(R.drawable.welcome_img),
                        contentDescription = "welcome_image"
                    )

                    Text(
                        text = "Hi ${userName ?: "Home"}",
                        fontSize = 23.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "Your smart network protection and performance monitor.Stay secure. Stay fast. Stay connected.",
                        fontSize = 15.sp,
                        color = Color.Gray, textAlign = TextAlign.Center

                    )

                    MainButton(
                        text = "Let's Go",
                        bgBrush = blueBrush,
                        contentColor = Color.White,
                        onClick = { navController.navigate("Main") }
                    )
                }
            }

        }


    }

}