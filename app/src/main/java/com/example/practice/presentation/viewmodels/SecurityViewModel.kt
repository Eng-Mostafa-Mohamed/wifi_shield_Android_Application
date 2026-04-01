package com.example.practice.presentation.viewmodels// ui/viewmodels/SecurityViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repoImpl.SecurityRepository
import com.example.domain.dataClasses.AlertModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SecurityViewModel : ViewModel() {
    private val repository = SecurityRepository()
    
    private val _uiState = MutableStateFlow<AlertModel?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.alertFlow.collect { alert ->
                _uiState.value = alert
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.disconnect()
    }
}