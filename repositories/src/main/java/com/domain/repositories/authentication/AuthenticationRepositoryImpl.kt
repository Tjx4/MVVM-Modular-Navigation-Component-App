package com.domain.repositories.authentication

import com.domain.core.persistance.room.ForexDB

class AuthenticationRepositoryImpl(private val database: ForexDB) : AuthenticationRepository {
    override fun loginUser(username: String, password: String) {

    }
}