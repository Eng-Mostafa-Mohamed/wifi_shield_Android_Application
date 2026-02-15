package com.example.practice.presentation.screens.AuthScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.practice.ui.theme.blueBrush
import com.example.practice.ui.theme.dark_blue
import com.example.practice.ui.theme.titleFont
import com.example.practice.presentation.viewmodels.RouterLoginViewModel
import com.example.practice.ui.theme.blue
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun RouterSetup(
    modifier: Modifier = Modifier,
    viewModel: RouterLoginViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    navController: NavController,
) {

    val state = viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    SetStatusBarColor(color = dark_blue, false)

    // Local states for input fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailState = remember { mutableStateOf(email) }
    val passwordState = remember { mutableStateOf(password) }

    // Show error snackbar or navigate on success
    LaunchedEffect(state) {
        state.error?.let { msg ->
            scope.launch { snackbarHostState.showSnackbar(msg) }
        }

        state.token?.let {
            onRegisterSuccess()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                    })
                }
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "Connect Router",
                fontSize = 31.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = titleFont,
                color = Color.Black
            )

            Spacer(Modifier.height(30.dp))

            // Username field
            MainTextField(
                label = "Username",
                placeholder = "Enter Router Username",
                state = emailState,
                onValueChange = { email = it },
                errorState = null,
                iconRes = R.drawable.username_ic
            )

            Spacer(Modifier.height(16.dp))




            // Password field
            MainTextField(
                label = "Password",
                placeholder = "Enter Router password",
                state = passwordState,
                onValueChange = { password = it },
                errorState = null,
                iconRes = R.drawable.password_ic,
                isPassword = true
            )

            Spacer(Modifier.height(24.dp))






            // Connect button
            MainButton(
                text = if (state.isLoading) "Connecting..." else "Connect",
                bgBrush = blueBrush,
                onClick = { viewModel.login(email, password) },
            )

            Spacer(Modifier.height(100.dp))

            // Optional: loading indicator
            if (state.isLoading) {
                CircularProgressIndicator(color = blue)
            }
        }
    }
}
