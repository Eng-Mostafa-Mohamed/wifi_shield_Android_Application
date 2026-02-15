package com.example.practice.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCases.CheckFirstTimeUserUseCase
import com.example.domain.useCases.SetFirstTimeUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val checkFirstTimeUser: CheckFirstTimeUserUseCase,
    private val setFirstTimeUser: SetFirstTimeUserUseCase
) : ViewModel() {

    var currentPage by mutableStateOf(0)
        private set

    var isFirstTimeUser by mutableStateOf<Boolean?>(null)
        private set

    init {
        viewModelScope.launch {
            isFirstTimeUser = checkFirstTimeUser()
        }
    }

    fun nextPage() { currentPage++ }
    fun previousPage() { if(currentPage > 0) currentPage-- }

    fun finishOnboarding(onFinished: () -> Unit) {
        viewModelScope.launch {
            setFirstTimeUser(false)
            onFinished()
        }
    }
}
