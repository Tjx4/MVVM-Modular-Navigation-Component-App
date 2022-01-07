package com.domain.repositories.authentication

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.LoginResponse
import com.domain.myapplication.models.Picture
import com.domain.myapplication.models.User

class AuthenticationRepositoryImpl(private val retrofitServices: RetrofitServices, private val mySqliteDB: MySqliteDB) : AuthenticationRepository {
    override suspend fun loginUser(username: String, password: String): LoginResponse? {
        return if(username == "" || password == "" ){
            null
        }
        else if(username == "email@domain.tld"  &&  password == "Pl@12345"){
            val user = User(
                username,
                "Tshepo",
                Picture(
                    "http://appicsoftware.xyz/demo/images/tshepo.jpg",
                    "http://appicsoftware.xyz/demo/images/tshepo.jpg",
                    "http://appicsoftware.xyz/demo/images/tshepo.jpg"
                )
            )

            LoginResponse(user, null)
        }
        else{
            LoginResponse(null, ErrorResponse("Incorrect login details"))
        }

        /*
        return try {
            retrofitServices.loginUser(username, password)
        }
        catch (ex: Exception){
            firebaseCrashlytics.recordException(ex)
            null
        }
        */
    }
}