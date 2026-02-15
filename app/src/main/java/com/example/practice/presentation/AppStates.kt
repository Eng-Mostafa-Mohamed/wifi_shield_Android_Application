package com.example.practice.presentation
data class RouterLoginUiState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)

data class ForgotPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val isSuccess: Boolean = false
)

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
sealed class LoginEvent {
    data class ShowMessage(val message: String) : LoginEvent()
    object NavigateToHome : LoginEvent()
}