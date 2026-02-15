package com.example.practice.presentation.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StepCircle(number: Int, isActive: Boolean) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(
                if (isActive) Color.White
                else Color.White.copy(alpha = 0.4f)
            )
    ) {
        Text(
            text = number.toString(),
            color = if (isActive) Color(0xFF6A1B9A) else Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

