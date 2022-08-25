package com.yoshio.test.domain.model

import com.yoshio.SignInMutation
import com.yoshio.fragment.UserFragment

data class User(val id: String,
                val jwtToken: String = "",
                val firstName: String,
                val lastName: String,
                val email: String,
                val phone: String,
                val birthDate: String = "")

fun SignInMutation.TokenCreate.toUser() = user?.userFragment?.toUser(
        token = token)

fun UserFragment.toUser(token: String?) = User(
        id = id,
        jwtToken = token.orEmpty(),
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone.orEmpty(),
        birthDate = birthdate.orEmpty()
)
