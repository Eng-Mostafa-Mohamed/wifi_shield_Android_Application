package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.ResetPasswordUseCase
import com.example.practice.presentation.ForgotPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value) }
    }

    fun onSendClick() {
        viewModelScope.launch {
            // Start loading
            _state.update { it.copy(isLoading = true, error = null) }

            resetPasswordUseCase(state.value.email)
                .onSuccess {
                    // Update state for success
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = "Reset link sent to your email",
                            isSuccess = true
                        )
                    }
                    // Emit success message to Snackbar
                    _event.emit("Reset link sent to your email")
                }
                .onFailure { throwable ->
                    // Stop loading
                    _state.update { it.copy(isLoading = false) }

                    // Emit error message to Snackbar
                    _event.emit(throwable.message ?: "Reset failed")
                }
        }
    }
}
