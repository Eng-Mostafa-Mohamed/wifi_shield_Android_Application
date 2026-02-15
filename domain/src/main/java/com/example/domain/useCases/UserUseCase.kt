package com.example.domain.useCases

import android.util.Patterns
import com.example.domain.dataClasses.User
import com.example.domain.repo.UserRepository


class ResetPasswordUseCase(
    private val repo: UserRepository
) {

    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(Exception("Email cannot be empty"))
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(Exception("Invalid email format"))
        }

        return repo.resetPassword(email)
    }
}

class RegisterUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repo.register(email, password)
}
class RouterLoginUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(email: String, password: String) : Result<String>{
        return  repo.routerLogin(email, password)
    }
}
class LoginUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repo.login(email, password)
}

class GetUserEmailUseCase(private val repo: UserRepository) {
    suspend operator fun invoke() =
        repo.getCurrentUserEmail()
}

class GetUserNameUseCase(private val repository: UserRepository) {

    suspend operator fun invoke(email: String): String? {
        return repository.getUserName(email)
    }
}

class AddUser(private val repository: UserRepository) {

    suspend operator fun invoke(user: User) {
        return repository.addUser(user)
    }
}