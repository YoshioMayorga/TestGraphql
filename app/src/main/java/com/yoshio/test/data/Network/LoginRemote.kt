package com.yoshio.test.data.Network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.yoshio.SignInMutation
import com.yoshio.test.domain.model.User
import com.yoshio.test.domain.model.toUser
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


class LoginRemote @Inject constructor(private val client: ApolloClient) {

    suspend fun signIn(email: String, password: String): Result<User> {

        var mutation = SignInMutation(email = email, password = password)

        return try {
            val response = client.mutation(mutation).execute()
            val tokenCreate = response.data?.tokenCreate
            val user = tokenCreate?.toUser()

            if (user == null || response.hasErrors()) {
                return Result.failure(Throwable(message = tokenCreate?.errors?.get(0)?.message))
            }
            Result.success(user)
        } catch (e: ApolloException) {
            Result.failure(e)
        }
    }
}