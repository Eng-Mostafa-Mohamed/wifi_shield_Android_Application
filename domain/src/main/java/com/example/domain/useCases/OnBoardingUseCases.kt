package com.example.domain.useCases

import com.example.domain.repo.OnboardingRepository

class CheckFirstTimeUserUseCase (private val repository: OnboardingRepository) {
    suspend operator fun invoke() = repository.isFirstTimeUser()
}

class SetFirstTimeUserUseCase(private val repository: OnboardingRepository) {
    suspend operator fun invoke(value: Boolean) = repository.setFirstTimeUser(value)
}