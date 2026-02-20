package com.example.data.core.modules

import com.example.data.core.IpReportApiService
import com.example.data.core.Ip_ApiService
import com.example.data.core.RetrofitInstance
import com.example.data.core.RouterApiService
import com.example.data.core.UserDao
import com.example.data.repoImpl.AuthRepositoryImpl
import com.example.data.repoImpl.DevicesRepositoryImpl
import com.example.data.repoImpl.IpReportRepositoryImpl
import com.example.data.repoImpl.PublicIpRepositoryImpl
import com.example.domain.repo.DevicesRepository
import com.example.domain.repo.IpReportRepository
import com.example.domain.repo.PublicIpRepository
import com.example.domain.repo.UserRepository
import com.example.domain.useCases.GetPublicIpUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideIpApiService(): Ip_ApiService = RetrofitInstance.api

    @Provides
    fun provideIpRepository(api: Ip_ApiService): PublicIpRepository =
        PublicIpRepositoryImpl(api)


    @Provides
    fun provideGetDevicesRepository(
        routerApi: RouterApiService,
    ): DevicesRepository =
        DevicesRepositoryImpl(routerApi)

    @Provides
    fun provideIpReportRepository(
       api: IpReportApiService
    ): IpReportRepository =
        IpReportRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: RouterApiService,
        auth: FirebaseAuth,
        userDao: UserDao
    ): UserRepository =
        AuthRepositoryImpl(api,userDao, auth)


}
