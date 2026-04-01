package com.example.practice.presentation.screens.AuthScreen

import ClickableTextCustom
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.practice.presentation.viewmodels.RegisterViewModel
import com.example.practice.ui.theme.HeaderFont

@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val primaryBlue = Color(0xFF1c1b69)

    SetStatusBarColor(color = Color.White, darkIcons = true)

    LaunchedEffect(Unit) {
        viewModel.event.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = primaryBlue,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                    })
                }
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Create account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HeaderFont,
                color = primaryBlue
            )

            Spacer(Modifier.height(40.dp))

            MainTextField(
                label = "Full Name",
                placeholder = "Enter your name",
                state = mutableStateOf(state.name),
                onValueChange = viewModel::onNameChange,
                errorState = null,
                iconRes = R.drawable.username_ic
            )

            Spacer(Modifier.height(8.dp))

            MainTextField(
                label = "Email",
                placeholder = "Enter your email",
                state = mutableStateOf(state.email),
                onValueChange = viewModel::onEmailChange,
                errorState = null,
                iconRes = R.drawable.email_ic
            )

            Spacer(Modifier.height(8.dp))

            MainTextField(
                label = "Password",
                placeholder = "Enter password",
                state = mutableStateOf(state.password),
                onValueChange = viewModel::onPasswordChange,
                errorState = state.error,
                iconRes = R.drawable.visibility_off,
                isPassword = true
            )

            Spacer(Modifier.height(32.dp))

            val buttonEnabled = !state.isLoading

            MainButton(
                text = "Register",
                bgBrush = Brush.horizontalGradient(
                    colors = listOf(primaryBlue, Color(0xFF2E2C96))
                )
            ) {
                if (buttonEnabled) {
                    viewModel.register()
                }
            }

            if (state.isSuccess) {
                LaunchedEffect(Unit) {
                    onRegisterSuccess()
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ClickableTextCustom(clickableColor = primaryBlue,
                fullText = "Already have an account? ",
                clickableText = "Log in"
            ) {
                navController.navigate("login")
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}