package com.example.practice.presentation.utilities

import StepLine
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TwoStepIndicator(currentStep: Int) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
    ) {

        StepCircle(number = 1, isActive = currentStep >= 1)

        StepLine(isActive = currentStep >= 2)

        StepCircle(number = 2, isActive = currentStep >= 2)
    }
}

