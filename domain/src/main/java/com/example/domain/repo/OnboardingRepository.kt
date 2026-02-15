package com.example.domain.repo

interface OnboardingRepository {
    suspend fun isFirstTimeUser(): Boolean
    suspend fun setFirstTimeUser(value: Boolean)
}