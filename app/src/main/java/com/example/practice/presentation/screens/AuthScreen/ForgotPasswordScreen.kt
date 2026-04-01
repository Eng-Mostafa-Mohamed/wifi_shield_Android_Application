package com.example.practice.presentation.screens.AuthScreen

import ClickableTextCustom
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerce.utils.MainButton
import com.example.e_commerce.utils.MainTextField
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.ForgotPasswordViewModel
import com.example.practice.ui.theme.HeaderFont
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableState")
@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navController: NavController,
    onSuccess: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val primaryBlue = Color(0xFF1c1b69)

    SetStatusBarColor(color = primaryBlue, darkIcons = false)

    LaunchedEffect(Unit) {
        viewModel.event.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryBlue)
                .pointerInput(Unit) {
                    detectTapGestures { keyboardController?.hide() }
                }
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                primaryBlue,
                                primaryBlue.copy(alpha = 0.9f),
                                primaryBlue.copy(alpha = 0.8f),
                            )
                        )
                    )
            ) {

                Canvas(modifier = Modifier.size(200.dp).offset(x = 180.dp, y = (-50).dp)) {
                    drawCircle(color = Color.White.copy(alpha = 0.05f))
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Spacer(Modifier.height(30.dp))

                    Text(
                        text = "Forgot Password?",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HeaderFont,
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "We’ll send you a reset link",
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                        fontFamily = HeaderFont,
                        fontSize = 16.sp
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
                    androidx.compose.material.Text(
                        text = "Reset Now ",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HeaderFont,
                        color = primaryBlue
                    )

                    Spacer(Modifier.height(40.dp))

                    MainTextField(
                        label = "Email",
                        placeholder = "Enter your email",
                        state = remember { mutableStateOf(state.email) },
                        onValueChange = viewModel::onEmailChange,
                        errorState = state.error,
                        iconRes = R.drawable.email_ic
                    )

                    Spacer(Modifier.height(24.dp))

                    MainButton(
                        text = "Send Reset Link",
                        bgBrush = Brush.horizontalGradient(
                            colors = listOf(primaryBlue, Color(0xFF2E2C96))
                        )
                    ) {
                        if (!state.isLoading) {
                            viewModel.onSendClick()
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    ClickableTextCustom(clickableColor = primaryBlue,
                        fullText = "Remember your password? ",
                        clickableText = "Log In"
                    ) {
                        navController.navigate("login")
                    }
                }
            }
        }
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            delay(1500)
            onSuccess()
        }
    }
}