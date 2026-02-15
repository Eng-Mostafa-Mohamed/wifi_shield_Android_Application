package com.example.data.core.modules

import com.example.data.core.Ip_ApiService
import com.example.data.core.RetrofitInstance
import com.example.data.repoImpl.PublicIpRepositoryImpl
import com.example.domain.repo.PublicIpRepository
import com.example.domain.useCases.GetPublicIpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideIpApiService(): Ip_ApiService = RetrofitInstance.api

    @Provides
    fun provideIpRepository(api: Ip_ApiService): PublicIpRepository =
        PublicIpRepositoryImpl(api)

    @Provides
    fun provideGetPublicIpUseCase(repository: PublicIpRepository) =
        GetPublicIpUseCase(repository)
}
