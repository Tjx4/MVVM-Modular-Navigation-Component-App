package com.domain.repositories.authentication

import com.domain.core.persistance.room.ForexDB
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.LoginResponse
import com.domain.myapplication.models.User

class AuthenticationRepositoryImpl(private val database: ForexDB) : AuthenticationRepository {
    override fun loginUser(username: String, password: String): LoginResponse? {
        return try {
            //retrofitHelper.convert(username, password)
            if(username == "email@domain.com"  &&  password == "P@12345"){
                val user = User(username, "")
                LoginResponse(user, null)
            }
            else{
                LoginResponse(null, ErrorResponse("Incorrect login details"))
            }
        }
        catch (ex: Exception){
            //firebaseCrashlytics.recordException(ex)
            null
        }
    }
}