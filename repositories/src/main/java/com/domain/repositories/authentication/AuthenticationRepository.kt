package com.domain.repositories.authentication

import com.domain.myapplication.models.LoginResponse

interface AuthenticationRepository {
    fun loginUser(username: String, password: String): LoginResponse?
}