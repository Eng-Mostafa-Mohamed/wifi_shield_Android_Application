package com.example.domain.repo

import com.example.domain.dataClasses.User

interface UserRepository {

    // Router API (Python Server)
    suspend fun routerLogin(routerUser: String, routerPass: String): Result<String>


    // Firebase (Identity)
    suspend fun register(email: String, password: String)
    suspend fun login(email: String, password: String)
    suspend fun resetPassword(email: String): Result<Unit>
    fun getCurrentUserEmail(): String?

    //  Room (Local Cache)
    suspend fun addUser(user: User)
    suspend fun getUserName(email: String): String?
    fun getCurrentUserId(): String?

}