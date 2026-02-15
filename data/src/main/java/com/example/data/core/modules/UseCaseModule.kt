package com.example.data.core.modules

import com.example.domain.repo.UserRepository
import com.example.domain.useCases.AddUser
import com.example.domain.useCases.GetUserEmailUseCase
import com.example.domain.useCases.GetUserNameUseCase
import com.example.domain.useCases.LoginUseCase
import com.example.domain.useCases.RegisterUseCase
import com.example.domain.useCases.ResetPasswordUseCase
import com.example.domain.useCases.RouterLoginUseCase
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
    @Singleton
    fun provideLoginUseCase(repository: UserRepository): LoginUseCase =
        LoginUseCase(repository)
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


}