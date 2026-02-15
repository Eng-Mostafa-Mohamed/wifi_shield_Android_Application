package com.example.data.core.modules

import NetworkRepositoryImpl
import com.example.domain.repo.NetworkRepository
import com.example.domain.useCases.PingTestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkRepository(): NetworkRepository =
        NetworkRepositoryImpl()

    @Provides
    fun providePingTestUseCase(
        repository: NetworkRepository
    ): PingTestUseCase = PingTestUseCase(repository)
}
