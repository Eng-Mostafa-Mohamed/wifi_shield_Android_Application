package com.example.domain.repo

import com.example.domain.dataClasses.User

interface UserRepository {

    //api
    suspend fun routerLogin(email: String, password: String): Result<String>


    // Firebase
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun resetPassword(email: String): Result<Unit>
    fun getCurrentUserEmail(): String?

    // Room
    suspend fun addUser(user: User)
    suspend fun getUserName(email: String): String?
}