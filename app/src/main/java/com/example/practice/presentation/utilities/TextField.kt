package com.example.e_commerce.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.ui.theme.white_blue
import com.example.practice.R

@Composable
fun MainTextField(
    label: String,
    placeholder: String,
    errorState: String?,
    state: MutableState<String>,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    @DrawableRes iconRes: Int? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            label,
            color = Color.Gray,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(top = 10.dp, start = 15.dp, bottom = 2.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.95f),
            value = state.value,
            onValueChange = {
                state.value = it
                onValueChange(it)
            },
            placeholder = { Text(placeholder) },

            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else VisualTransformation.None,

            trailingIcon = {
                if (isPassword) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible)
                                R.drawable.visable
                            else
                                R.drawable.visibility_off
                        ),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                } else {
                    iconRes?.let { res ->
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedBorderColor = white_blue,
                unfocusedBorderColor = Color.Gray
            )
        )

        if (!errorState.isNullOrEmpty()) {
            Text(
                text = errorState,
                color = Color.Red,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(top = 2.dp, start = 15.dp)
                    .align(Alignment.Start)
            )
        }
    }
}
