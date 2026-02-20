package com.example.practice.presentation.screens.AuthScreen

import ClickableTextCustom
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.practice.presentation.LoginEvent
import com.example.practice.presentation.viewmodels.LoginViewModel
import com.example.practice.ui.theme.HeaderFont
import com.example.practice.ui.theme.blue
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.move


@SuppressLint("UnrememberedMutableState")
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Status bar matches the new Deep Midnight background
    SetStatusBarColor(color = Color(0xFF101828), darkIcons = false)

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
                LoginEvent.NavigateToHome -> {
                    navController.navigate("welcome") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(blueBrush)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { keyboardController?.hide() })
                }
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .shadow(20.dp, RoundedCornerShape(28.dp), ambientColor = move, spotColor = move)
                    .background(
                        brush = blueBrush,
                        shape = RoundedCornerShape(28.dp)
                    )
                    ,
                contentAlignment = Alignment.CenterStart
            ) {

                Canvas(modifier = Modifier.size(200.dp).offset(x = 180.dp, y = (-50).dp)) {
                    drawCircle(color = Color.White.copy(alpha = 0.1f))
                }
                Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                    Text(
                        text = "WIFI SHIELD",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Secure Connection",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = HeaderFont
                    )
                    Text(
                        text = "Login to monitor your network protection.",
                        color = Color.White.copy(alpha = 0.6f), // Softer white
                        fontSize = 14.sp
                    )
                }
            }


            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White) // Clean White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(40.dp))

                    Text(
                        text = "Welcome back!",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = blue
                    )

                    Spacer(Modifier.height(32.dp))

                    MainTextField(
                        label = "Secure Email",
                        placeholder = "name@company.com",
                        state = remember { mutableStateOf(state.email) },
                        onValueChange = viewModel::onEmailChange,
                        iconRes = R.drawable.email_ic,
                        errorState = ""
                    )

                    Spacer(Modifier.height(16.dp))

                    MainTextField(
                        label = "Access Key",
                        placeholder = "••••••••",
                        state = remember { mutableStateOf(state.password) },
                        onValueChange = viewModel::onPasswordChange,
                        isPassword = true,
                        iconRes = R.drawable.password_ic,
                        errorState = ""
                    )

                    Spacer(Modifier.height(12.dp))

                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        ClickableTextCustom(
                            fullText = "",
                            clickableText = "Recovery Access?",
                            // Ensure ClickableTextCustom inside uses Color(0xFF0284C7)
                        ) {
                            navController.navigate("resetPass")
                        }
                    }

                    Spacer(Modifier.height(32.dp))

                    MainButton(
                        text ="UNLOCK SHIELD",
                        bgBrush =blueBrush
                    ) {
                        if (!state.isLoading) viewModel.login()
                    }

                    Spacer(Modifier.weight(1f))

                    // Footer
                    Row(
                        modifier = Modifier.padding(bottom = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("New to Shield? ", color = Color(0xFF667085))
                        ClickableTextCustom(
                            fullText = "",
                            clickableText = "Create Identity"
                        ) {
                            navController.navigate("MainReg")
                        }
                    }
                }
            }
        }
    }
}