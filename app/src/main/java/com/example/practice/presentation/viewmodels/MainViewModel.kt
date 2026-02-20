package com.example.practice.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataClasses.IpReportUiState
import com.example.domain.useCases.GetIpReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.useCases.GetPublicIpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPublicIpUseCase: GetPublicIpUseCase,
    private val getIpReportUseCase: GetIpReportUseCase
) : ViewModel() {

    private val _reportState = MutableStateFlow(IpReportUiState())
    val reportState: StateFlow<IpReportUiState> = _reportState
    private val _ipState = MutableStateFlow<String?>(null)
    val ipState: StateFlow<String?> = _ipState



    init {
        fetchIp()
    }


    fun fetchIp() {
        viewModelScope.launch {
            try {
                val ipInfo = getPublicIpUseCase()
                _ipState.value = ipInfo.ip

                ipInfo.ip?.let {
                    getData(it)
                }

            } catch (e: Exception) {
                _ipState.value = "Error fetching IP"
            }
        }
    }



    fun getData(ip: String) {
        viewModelScope.launch {

            _reportState.value = IpReportUiState(isLoading = true)

            try {
                val data = getIpReportUseCase(ip)

                _reportState.value = IpReportUiState(
                    isLoading = false,
                    data = data
                )

            } catch (e: Exception) {
                _reportState.value = IpReportUiState(
                    isLoading = false,
                    error = e.message ?: "Unknown Error"
                )
            }
        }
    }



}
