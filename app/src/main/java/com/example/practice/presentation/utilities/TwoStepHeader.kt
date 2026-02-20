package com.example.practice.presentation.utilities
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.ui.theme.HeaderFont


@Composable
fun TwoStepHeader(currentStep: Int) {

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
                ),
                shape = RoundedCornerShape(
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .padding(30.dp)
    ) {
        Canvas(modifier = Modifier.size(200.dp).offset(x = 180.dp, y = (-50).dp)) {
            drawCircle(color = Color.White.copy(alpha = 0.1f))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Column {
                Text(
                    text = "Hello,",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = HeaderFont,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Get Started ! ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = HeaderFont,
                    fontSize = 22.sp,
                )
            }


            TwoStepIndicator(currentStep)
        }
    }
}




