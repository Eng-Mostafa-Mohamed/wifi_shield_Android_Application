package com.example.practice.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.core.modules.SessionManager
import com.example.practice.presentation.screens.AuthScreen.LoginScreen
import com.example.onboarding.OnboardingScreen
import com.example.practice.presentation.screens.AuthScreen.ForgotPasswordScreen
import com.example.practice.presentation.screens.AuthScreen.RegisterScreen
import com.example.practice.presentation.screens.AuthScreen.RegistrationScreen
import com.example.practice.presentation.screens.AuthScreen.RouterSetup
import com.example.practice.presentation.screens.MainScreen.MainScreen
import com.example.practice.presentation.screens.ToolsScreen.ToolsScreen
import com.example.practice.presentation.screens.WelcomeScreen.WelcomeScreen
import com.example.practice.presentation.screens.devices.BlockedDevicesScreen
import com.example.practice.presentation.screens.devices.DevicesScreen
import com.example.practice.presentation.screens.GuideScreen.GuideScreen
import com.example.practice.presentation.screens.Test.FullWifiSettingsScreen
import com.example.practice.presentation.viewmodels.OnboardingViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        NavHost(
            navController = navController,
            startDestination = "launcher",
            modifier = Modifier.padding(padding)
        ) {

            composable("launcher") {
                val viewModel: OnboardingViewModel = hiltViewModel()
                LauncherScreen(viewModel, navController)
            }

            composable("onboarding") {
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(viewModel) {
                    navController.navigate("login") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }
            composable("register") { RegisterScreen(navController, onRegisterSuccess = {
                navController.navigate("routerSetup") {
                    popUpTo("register") { inclusive = true }
                }

            }) }
            composable("login") { LoginScreen(navController) }
            composable("routerSetup") { RouterSetup(navController = navController, onRegisterSuccess = {
                navController.navigate("login") {
                    popUpTo("routerSetup") { inclusive = true }
                }
            } )}
            composable("MainReg") { RegistrationScreen(navController) }
            composable("resetPass") { ForgotPasswordScreen(navController = navController) { } }
            composable("welcome") { WelcomeScreen(navController) }
            composable("Main") { MainScreen(navController) }
            composable("Guide") { GuideScreen() }
            composable("connectedDevices") { DevicesScreen(navController) }
            composable("blockedDevices") { BlockedDevicesScreen(navController) }
            composable("tools") { ToolsScreen() }
            composable("wifiSettings") { FullWifiSettingsScreen(navController) }


        }
    }
}



@Composable
fun LauncherScreen(viewModel: OnboardingViewModel, navController: NavController) {
    val firstTime = viewModel.isFirstTimeUser

    if (firstTime == null) {
        Box(modifier = Modifier.fillMaxSize()) { }
    } else if (firstTime) {
        LaunchedEffect(Unit) {
            navController.navigate("onboarding") {
                popUpTo("launcher") { inclusive = true }
            }
        }
    } else {
        val token = SessionManager.authToken
        if (!token.isNullOrEmpty()) {
            LaunchedEffect(Unit) {
                navController.navigate("MainReg") {
                    popUpTo("launcher") { inclusive = true }
                }
            }
        } else {
            LaunchedEffect(Unit) {
                navController.navigate("Main") {
                    popUpTo("launcher") { inclusive = true }
                }
            }
        }
    }
}
