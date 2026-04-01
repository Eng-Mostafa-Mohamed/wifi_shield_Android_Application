package com.example.data.repoImpl

import com.example.data.core.RouterApiService
import com.example.data.core.UserDao
import com.example.data.core.modules.SessionManager
import com.example.data.mapper.toUserDto
import com.example.data.modules.LoginRequest
import com.example.data.modules.RouterLinkRequest
import com.example.domain.dataClasses.User
import com.example.domain.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val routerApi: RouterApiService,
    private val userDao: UserDao,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun routerLogin(routerUser: String, routerPass: String): Result<String> {
        return try {
            val currentUser = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No Firebase User found"))

            val request = RouterLinkRequest(
                user_id = currentUser.uid,
                email = currentUser.email ?: "",
                email_password = "APPLICATION_PASSWORD", // اختياري حسب حاجتك
                router_username = routerUser,
                router_password = routerPass
            )

            val response = routerApi.registerRouter(request) // الميثود الجديدة في الـ API

            if (response.isSuccessful) {
                Result.success("Router Linked Successfully")
            } else {
                Result.failure(Exception("Failed to link router: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun addUser(user: User) {
        val entity = user.toUserDto()
        userDao.addUser(entity)
    }

    override suspend fun getUserName(email: String): String? {
        return userDao.getUserName(email)
    }
}