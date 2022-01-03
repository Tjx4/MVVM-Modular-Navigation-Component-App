package com.domain.repositories.authentication

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.LoginResponse
import com.domain.myapplication.models.User

class AuthenticationRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: MySqliteDB) : AuthenticationRepository {
    override suspend fun loginUser(username: String, password: String): LoginResponse? {
        return try {
            //retrofitServices.loginUser(username, password)
            if(username == "" || password == "" ){
                null
            }
            else if(username == "email@domain.com"  &&  password == "Pl@12345"){
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