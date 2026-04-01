package com.example.practice.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.RouterLoginUseCase
import com.example.practice.presentation.RouterLoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RouterLoginViewModel @Inject constructor(
    private val loginUseCase: RouterLoginUseCase
) : ViewModel() {

    var state by mutableStateOf(RouterLoginUiState())
        private set

    fun login(routerUser: String, routerPass: String) {

        if (routerUser.isBlank() || routerPass.isBlank()) {
            state = state.copy(error = "Please fill all router fields")
            return
        }

        viewModelScope.launch {

            state = state.copy(isLoading = true, error = null)

            val result = loginUseCase(routerUser, routerPass)

            result.onSuccess { message ->
                state = state.copy(
                    isLoading = false,
                    token = message
                )
            }

            result.onFailure { exception ->
                state = state.copy(
                    isLoading = false,
                    error = exception.message ?: "Failed to connect to server"
                )
            }
        }
    }
}