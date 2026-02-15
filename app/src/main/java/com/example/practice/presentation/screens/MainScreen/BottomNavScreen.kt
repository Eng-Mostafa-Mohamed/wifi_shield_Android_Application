package com.example.practice.presentation.screens.MainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person


sealed class BottomNavScreen(val title: String, val icon: Any) {
    object Home : BottomNavScreen("Home", Icons.Default.Home)
    object Devices : BottomNavScreen("Devices", Icons.Default.Person)
    object Information : BottomNavScreen("Guide",com.example.practice.R.drawable.security_icon_135219)
    object Tools : BottomNavScreen("Tools", com.example.practice.R.drawable.more_icon)
}