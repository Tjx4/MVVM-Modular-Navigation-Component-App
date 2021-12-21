package com.domain.repositories.authentication

interface AuthenticationRepository {
    fun loginUser(username: String, password: String)
}