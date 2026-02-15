package com.example.data.core.modules

import NetworkRepositoryImpl
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.repoImpl.OnboardingRepositoryImpl
import com.example.domain.repo.NetworkRepository
import com.example.domain.repo.OnboardingRepository
import com.example.domain.useCases.CheckFirstTimeUserUseCase
import com.example.domain.useCases.SetFirstTimeUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardUModule {

    @Provides
    fun provideOnBoardRepository(dataStore: DataStore<Preferences>): OnboardingRepository =
        OnboardingRepositoryImpl(dataStore)

    @Provides
    @Singleton
    fun provideFirstTimeUserUseCase(repository: OnboardingRepository): CheckFirstTimeUserUseCase =
        CheckFirstTimeUserUseCase(repository)

    @Provides
    @Singleton
    fun provideSetFirstTimeUserUseCase(repository: OnboardingRepository): SetFirstTimeUserUseCase =
        SetFirstTimeUserUseCase(repository)

}