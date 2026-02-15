package com.example.practice.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.GetUserEmailUseCase
import com.example.domain.useCases.GetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase
): ViewModel() {


    private val _username = mutableStateOf<String?>(null)
    val username: State<String?> = _username



    init {
        viewModelScope.launch {
            try {
                val email = getUserEmailUseCase()
                if (email != null) {
                    val name = getUserNameUseCase(email) ?: "Home"
                    _username.value = name
                } else {
                    _username.value = "Home"
                }
            } catch (e: Exception) {
                _username.value = "Home"
            }
        }
    }



}