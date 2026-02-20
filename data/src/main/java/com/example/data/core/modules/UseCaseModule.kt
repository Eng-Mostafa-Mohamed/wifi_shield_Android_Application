package com.example.data.core.modules

import com.example.domain.repo.DevicesRepository
import com.example.domain.repo.IpReportRepository
import com.example.domain.repo.PublicIpRepository
import com.example.domain.repo.UserRepository
import com.example.domain.useCases.AddUser
import com.example.domain.useCases.BlockDeviceUseCase
import com.example.domain.useCases.DeleteDeviceUseCase
import com.example.domain.useCases.GetBlockedDevicesUseCase
import com.example.domain.useCases.GetConnectedDevicesUseCase
import com.example.domain.useCases.GetIpReportUseCase
import com.example.domain.useCases.GetPublicIpUseCase
import com.example.domain.useCases.GetUserEmailUseCase
import com.example.domain.useCases.GetUserNameUseCase
import com.example.domain.useCases.LoginUseCase
import com.example.domain.useCases.RegisterUseCase
import com.example.domain.useCases.ResetPasswordUseCase
import com.example.domain.useCases.RouterLoginUseCase
import com.example.domain.useCases.UnBlockDeviceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: UserRepository): RegisterUseCase =
        RegisterUseCase(repository)

    @Provides
    fun provideGetPublicIpUseCase(repository: PublicIpRepository) =
        GetPublicIpUseCase(repository)
    @Provides
    @Singleton
    fun provideLoginUseCase(repository: UserRepository): LoginUseCase =
        LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideGetDevicesUseCase(repository: DevicesRepository): GetConnectedDevicesUseCase =
        GetConnectedDevicesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetBlockedUseCase(repository: DevicesRepository): GetBlockedDevicesUseCase =
        GetBlockedDevicesUseCase(repository)

    @Provides
    @Singleton
    fun provideBlockDeviceUseCase(repository: DevicesRepository): BlockDeviceUseCase =
        BlockDeviceUseCase(repository)

    @Provides
    @Singleton
    fun provideUnBLockDeviceUseCase(repository: DevicesRepository): UnBlockDeviceUseCase =
        UnBlockDeviceUseCase(repository)
    @Provides
    @Singleton
    fun provideDeleteDeviceUseCase(repository: DevicesRepository): DeleteDeviceUseCase =
        DeleteDeviceUseCase(repository)
    @Provides
    @Singleton
    fun provideRouterLoginUseCase(repository: UserRepository): RouterLoginUseCase =
        RouterLoginUseCase(repository)
    @Provides
    @Singleton
    fun provideResetPasswordUseCase(repository: UserRepository): ResetPasswordUseCase =
        ResetPasswordUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserEmailUseCase(repository: UserRepository): GetUserEmailUseCase =
        GetUserEmailUseCase(repository)

    @Provides
    @Singleton
    fun provideAddUserUseCase(repository: UserRepository): AddUser =
        AddUser(repository)

    @Provides
    @Singleton
    fun provideGetUserNameUseCase(repository: UserRepository): GetUserNameUseCase =
        GetUserNameUseCase(repository)

    @Provides
    @Singleton
    fun provideGetIpReporteUseCase(repository: IpReportRepository): GetIpReportUseCase =
        GetIpReportUseCase(repository)


}