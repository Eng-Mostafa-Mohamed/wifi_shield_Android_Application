package com.example.practice.presentation.screens.AuthScreen

import ClickableTextCustom
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce.utils.MainButton
import com.example.e_commerce.utils.MainTextField
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.LoginEvent
import com.example.practice.presentation.viewmodels.LoginViewModel
import com.example.practice.ui.theme.HeaderFont
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.move
import com.example.practice.ui.theme.primaryColor
import com.example.practice.ui.theme.secondaryColor
import com.example.practice.ui.theme.titleFont

@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    SetStatusBarColor(color = dark_blue,false)

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {

                is LoginEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                LoginEvent.NavigateToHome -> {
                    navController.navigate("welcome") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {     SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { data ->
                androidx.compose.material3.Snackbar(
                    snackbarData = data,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
            }
        ) }
    ) { padding ->

        Column(
            modifier = Modifier.background( Color(0xFFc562fb))
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                    })
                }

                .padding(padding)
               ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(modifier = Modifier.fillMaxWidth().height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8f59fd),
                            Color(0xFFb560f9),
                            Color(0xFFc562fb),
                        )
                    ),
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Hello,",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HeaderFont,
                    )
                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Welcome back",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HeaderFont,
                    )
                }

            }



            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = "Access Your Shield",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = titleFont,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(16.dp))

                    MainTextField(
                        label = "Email",
                        placeholder = "Enter your email",
                        state = remember { mutableStateOf(state.email) },
                        onValueChange = viewModel::onEmailChange,
                        errorState = null,
                        iconRes = R.drawable.email_ic

                    )

                    Spacer(Modifier.height(12.dp))

                    MainTextField(
                        label = "Password",
                        placeholder = "Enter your password",
                        state = remember { mutableStateOf(state.password) },
                        onValueChange = viewModel::onPasswordChange,
                        errorState = null,
                        iconRes = R.drawable.password_ic,
                        isPassword = true


                    )

                    Spacer(Modifier.height(24.dp))

                    MainButton(
                        text = "Login",
                        bgBrush = blueBrush
                    ) {
                        if (!state.isLoading) {
                            viewModel.login()
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ClickableTextCustom(
                            fullText = "",
                            clickableText = "Forgot Password"
                        ) {
                            navController.navigate("resetPass")
                        }

                        ClickableTextCustom(
                            fullText = "",
                            clickableText = "Sign Up"
                        ) {
                            navController.navigate("MainReg")
                        }
                    }
                }
            }



        }
    }
}
