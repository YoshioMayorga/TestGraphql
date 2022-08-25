package com.yoshio.test.data

import com.yoshio.test.data.Network.LoginRemote
import com.yoshio.test.domain.model.User
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginRemote: LoginRemote){
    suspend fun signIn(email: String, password: String): Result<User> {
        return loginRemote.signIn(email,password)
    }
}