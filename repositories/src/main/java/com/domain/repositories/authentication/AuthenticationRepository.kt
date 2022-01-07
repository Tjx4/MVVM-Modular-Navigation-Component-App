package com.domain.repositories.authentication

import com.domain.myapplication.models.LoginResponse

interface AuthenticationRepository {
    suspend fun loginUser(username: String, password: String): LoginResponse?
    suspend fun logOutUser()
    fun isUserLoggedIn(): Boolean
}