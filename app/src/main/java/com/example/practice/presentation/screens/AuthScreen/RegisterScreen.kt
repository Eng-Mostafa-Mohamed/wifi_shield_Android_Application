package com.example.practice.presentation.screens.AuthScreen

import ClickableTextCustom
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce.utils.MainButton
import com.example.e_commerce.utils.MainTextField
import androidx.navigation.NavController
import com.example.practice.R
import com.example.practice.SetStatusBarColor
import com.example.practice.presentation.viewmodels.RegisterViewModel
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.titleFont



@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    SetStatusBarColor(color = dark_blue,false)
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
                    androidx.compose.material3.Snackbar(
                        snackbarData = data,
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                }
            )
        }

    ) {padding ->
        Column(
            modifier = Modifier
                .fillMaxSize().background(color = Color.White).
                pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                    })
                }

                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {

            Text(
                text = "Create  account",
                fontSize = 31.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = titleFont,
                color = Color.Black
            )
            Spacer(Modifier.height(30.dp))

            MainTextField(
                label = "Full Name",
                placeholder = "Enter your name",
                state = mutableStateOf(state.name),
                onValueChange = viewModel::onNameChange,
                errorState = null,
                iconRes = R.drawable.username_ic
            )

            MainTextField(
                label = "Email",
                placeholder = "Enter your email",
                state = mutableStateOf(state.email),
                onValueChange = viewModel::onEmailChange,
                errorState = null,
                iconRes = R.drawable.email_ic
            )

            MainTextField(
                label = "Password",
                placeholder = "Enter password",
                state = mutableStateOf(state.password),
                onValueChange = viewModel::onPasswordChange,
                errorState = state.error,
                iconRes = R.drawable.visibility_off,
                isPassword = true
            )

            Spacer(Modifier.height(16.dp))

            val buttonEnabled = !state.isLoading

            MainButton(
                text = "Register", bgBrush = blueBrush
            )

            {
                if (buttonEnabled) {
                    viewModel.register()
                }
            }


            if (state.isSuccess) {
                LaunchedEffect(Unit) {
                    onRegisterSuccess()
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            ClickableTextCustom(
                fullText = "Already have an account? ",
                clickableText = "Log in"
            ) {
                navController.navigate("login")
            }
            Spacer(Modifier.height(30.dp))
        }
    }

}
