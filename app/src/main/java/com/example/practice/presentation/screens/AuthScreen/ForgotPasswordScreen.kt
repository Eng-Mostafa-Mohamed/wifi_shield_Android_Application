package com.example.practice.presentation.screens.AuthScreen

import ClickableTextCustom
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import com.example.practice.presentation.viewmodels.ForgotPasswordViewModel
import com.example.practice.ui.theme.HeaderFont
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.move
import com.example.practice.ui.theme.secondaryColor
import com.example.practice.ui.theme.titleFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    SetStatusBarColor(color = dark_blue, false)

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
                .background(Color(0xFFc562fb))
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
                                Color(0xFF8f59fd),
                                Color(0xFFb560f9),
                                Color(0xFFc562fb),
                            )
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

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
                        color = Color.White,
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
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = titleFont,
                        color = Color.Black
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
                        bgBrush = blueBrush
                    ) {
                        if (!state.isLoading) {
                            viewModel.onSendClick()
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    ClickableTextCustom(
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
