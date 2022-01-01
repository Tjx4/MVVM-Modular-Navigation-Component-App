package com.domain.repositories.authentication

import com.domain.core.persistance.room.ForexDB
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.LoginResponse

class AuthenticationRepositoryImpl(private val database: ForexDB) : AuthenticationRepository {
    override fun loginUser(username: String, password: String): LoginResponse? {
        return try {
            //retrofitHelper.convert(username, password)
            LoginResponse(null, ErrorResponse("Incorrect password"))
        }
        catch (ex: Exception){
            //firebaseCrashlytics.recordException(ex)
            null
        }
    }
}