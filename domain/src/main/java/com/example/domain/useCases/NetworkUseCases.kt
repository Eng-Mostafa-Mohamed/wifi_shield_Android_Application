package com.example.domain.useCases

import com.example.domain.dataClasses.PingResult
import com.example.domain.repo.NetworkRepository

class PingTestUseCase(private val repository: NetworkRepository){

    suspend operator fun invoke(): PingResult{
        return repository.pingTest()
    }

}