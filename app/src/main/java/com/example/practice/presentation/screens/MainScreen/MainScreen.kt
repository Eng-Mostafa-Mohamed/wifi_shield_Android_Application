package com.example.practice.presentation.screens.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.presentation.screens.ToolsScreen.ToolsScreen
import com.example.practice.presentation.screens.devices.DevicesScreen
import com.example.practice.presentation.screens.homeScreen.HomeScreen
import com.example.practice.ui.theme.bg_gray
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.example.practice.presentation.screens.infoScreen.AboutScreen
import com.example.practice.ui.theme.move
import com.example.practice.ui.theme.white_blue

@Composable
fun MainScreen() {

    var selectedTab by remember { mutableStateOf<BottomNavScreen>(BottomNavScreen.Tools) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_gray)
    ) {


        when (selectedTab) {
            BottomNavScreen.Home -> HomeScreen()
            BottomNavScreen.Devices -> DevicesScreen()
            BottomNavScreen.Information -> AboutScreen()
            BottomNavScreen.Tools -> ToolsScreen()
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(24.dp)
                )
                .background(
                    color = Color.White, // White background
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 1.dp,
                    color = white_blue,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val items = listOf(
                    BottomNavScreen.Home,
                    BottomNavScreen.Devices,
                    BottomNavScreen.Information,
                    BottomNavScreen.Tools
                )

                items.forEach { screen ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                selectedTab = screen
                            }
                    ) {
                        when (val icon = screen.icon) {
                            is ImageVector -> Icon(
                                imageVector = icon,
                                contentDescription = screen.title,
                                tint = if (selectedTab == screen) white_blue else Color.Gray
                            )
                            is Int -> Icon(
                                painter = painterResource(id = icon),
                                contentDescription = screen.title,
                                modifier = Modifier.height(20.dp),
                                tint = if (selectedTab == screen) white_blue else Color.Gray
                            )
                        }
                        Text(
                            text = screen.title,
                            fontSize = 14.sp,
                            color = if (selectedTab == screen) white_blue else Color.Gray
                        )
                    }
                }
            }
        }
    }
}
