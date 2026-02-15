package com.example.practice.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.domain.useCases.GetPublicIpUseCase
import com.example.domain.useCases.GetUserEmailUseCase
import com.example.domain.useCases.GetUserNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPublicIpUseCase: GetPublicIpUseCase,
) : ViewModel() {



    init {
        fetchIp()
    }
    private val _ipState = MutableStateFlow<String?>(null)
    val ipState: StateFlow<String?> = _ipState

    fun fetchIp() {
        viewModelScope.launch {
            try {
                val ipInfo = getPublicIpUseCase()
                _ipState.value = ipInfo.ip
            } catch (e: Exception) {
                _ipState.value = "Error fetching IP"
            }
        }
    }


}
