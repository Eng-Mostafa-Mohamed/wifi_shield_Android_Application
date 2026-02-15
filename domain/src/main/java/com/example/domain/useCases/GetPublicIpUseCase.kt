package com.example.domain.useCases

import com.example.domain.dataClasses.IpResponse
import com.example.domain.repo.PublicIpRepository

class GetPublicIpUseCase (private val repository: PublicIpRepository) {
    suspend operator fun invoke(): IpResponse{
        return repository.getPublicIp()
    }

}