package com.yoshio.test.domain

import com.yoshio.test.data.LoginRepository
import com.yoshio.test.domain.model.User
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
        private val repository: LoginRepository
){
    suspend fun signIn(email: String, password: String): Result<User> {
        return repository.signIn(email,password)
    }
}