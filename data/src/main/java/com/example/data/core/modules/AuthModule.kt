package com.example.data.core.modules

import android.content.Context
import com.example.data.core.IpReportApiService
import com.example.data.core.RetrofitInstanceForIpReport
import com.example.data.core.RetrofitInstanceForRouter
import com.example.data.core.RouterApiService
import com.example.data.core.UserDao
import com.example.data.core.UserDatabase
import com.example.data.repoImpl.AuthRepositoryImpl
import com.example.domain.useCases.AddUser
import com.example.domain.useCases.GetUserEmailUseCase
import com.example.domain.useCases.GetUserNameUseCase
import com.example.domain.useCases.LoginUseCase
import com.example.domain.useCases.RegisterUseCase
import com.example.domain.useCases.ResetPasswordUseCase
import com.example.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        UserDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase): UserDao =
        database.getUserDao()


    @Provides
    @Singleton
    fun provideRouterApi(): RouterApiService =
        RetrofitInstanceForRouter.router_api

    @Provides
    @Singleton
    fun provideIpReportApi(): IpReportApiService =
        RetrofitInstanceForIpReport.IpReportApi


    @Module
    @InstallIn(SingletonComponent::class)
    object CoreModule {


    }


}


