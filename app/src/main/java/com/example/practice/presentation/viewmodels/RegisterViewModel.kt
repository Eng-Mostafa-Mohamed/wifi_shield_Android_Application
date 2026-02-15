package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.AddUser
import com.example.domain.useCases.RegisterUseCase
import com.example.domain.dataClasses.User
import com.example.practice.presentation.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val addUserUseCase: AddUser,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    fun onNameChange(value: String) {
        _state.update { it.copy(name = value) }
    }

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value) }
    }

    fun register() {
        viewModelScope.launch {

            if (state.value.email.isBlank() || state.value.password.length < 6) {
                _event.emit("Invalid data")
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            runCatching {
                registerUseCase(state.value.email, state.value.password)
            }.onSuccess {

                val user = User(
                    email = state.value.email,
                    username = state.value.name
                )

                addUserUseCase(user)

                _state.update {
                    it.copy(isLoading = false, isSuccess = true)
                }

                _event.emit("Registration Successful ")

            }.onFailure { exception ->

                _state.update {
                    it.copy(isLoading = false)
                }

                _event.emit(exception.message ?: "Register failed")
            }
        }
    }
}
