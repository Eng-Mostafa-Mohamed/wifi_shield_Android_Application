package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.LoginUseCase
import com.example.practice.presentation.LoginEvent
import com.example.practice.presentation.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value) }
    }

    fun login() {
        viewModelScope.launch {

            if (state.value.email.isBlank() || state.value.password.isBlank()) {
                _event.emit(LoginEvent.ShowMessage("Please fill all fields"))
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            runCatching {
                loginUseCase(state.value.email, state.value.password)
            }.onSuccess {

                _state.update { it.copy(isLoading = false) }

                _event.emit(LoginEvent.NavigateToHome)

            }.onFailure {

                _state.update { it.copy(isLoading = false) }

                _event.emit(LoginEvent.ShowMessage("Login failed"))
            }
        }
    }
}
