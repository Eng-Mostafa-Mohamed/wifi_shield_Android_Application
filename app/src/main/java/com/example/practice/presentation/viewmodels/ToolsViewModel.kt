package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataClasses.PingResult
import com.example.domain.useCases.PingTestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val pingTestUseCase: PingTestUseCase
) : ViewModel() {

    private val _ping = MutableStateFlow<PingResult?>(null)
    val ping: StateFlow<PingResult?> = _ping

    fun runPingTest() {
        viewModelScope.launch {
            _ping.value = pingTestUseCase()
        }
    }
}
