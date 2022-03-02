package com.domain.repositories.authentication

import com.domain.myapplication.models.APIResponse
import com.domain.myapplication.models.User

interface AuthenticationRepository {
    suspend fun loginUser(username: String, password: String): APIResponse
    suspend fun logOutUser()
    fun isUserLoggedIn(): Boolean
    suspend fun updatePassword(oldPassword: String, newPassword: String)
    suspend fun getPreviousUsers(oldPassword: String, newPassword: String): List<User>
}