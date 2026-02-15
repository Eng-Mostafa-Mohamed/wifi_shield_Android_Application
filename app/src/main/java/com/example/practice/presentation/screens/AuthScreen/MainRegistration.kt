package com.example.practice.presentation.screens.AuthScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practice.presentation.utilities.TwoStepHeader



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegistrationScreen(navController: NavController) {

    var currentStep by remember { mutableStateOf(1) }

    Scaffold(backgroundColor =    Color(0xFFc562fb)) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                TwoStepHeader(currentStep)

                Card(
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {

                        when (currentStep) {
                            1 -> RegisterScreen(
                                navController,
                                onRegisterSuccess = {
                                    currentStep = 2
                                }
                            )
                            2 -> RouterSetup(
                                navController=navController,
                                onRegisterSuccess = {
                                    navController.navigate("welcome")
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}



