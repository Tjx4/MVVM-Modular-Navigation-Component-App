package com.domain.repositories.authentication

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.core.persistance.sharedPrefs.SharedPrefs
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.LoginResponse
import com.domain.myapplication.models.Image
import com.domain.myapplication.models.User

class AuthenticationRepositoryImpl(private val retrofitServices: RetrofitServices, private val mySqliteDB: MySqliteDB, private val sharedPrefs: SharedPrefs) : AuthenticationRepository {
    override suspend fun loginUser(username: String, password: String): LoginResponse? {
        return if(username == "" || password == "" ){
            null
        }
        else if(username == "email@domain.tld"  &&  password == "Pl@12345"){
            val picture = Image(
                "http://appicsoftware.xyz/demo/images/tshepo.jpg",
                "http://appicsoftware.xyz/demo/images/tshepo.jpg",
                "http://appicsoftware.xyz/demo/images/tshepo.jpg"
            )

            val user = User(username, "Tshepo", picture)

            sharedPrefs.currentUser = user
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

    override suspend fun logOutUser() {
        sharedPrefs.currentUser = null
    }

    override fun isUserLoggedIn() = sharedPrefs.currentUser != null

    override suspend fun updatePassword(oldPassword: String, newPassword: String) {
        val data = hashMapOf<String, String>(Pair(oldPassword, newPassword))
        retrofitServices.updatePassword(data)
    }

    override suspend fun getPreviousUsers(oldPassword: String, newPassword: String): List<User> {
        val previousUsers = ArrayList<User>()

        mySqliteDB.usersDAO.getAllUsers()?.forEach {
            val image = Image(it.imageThumbNail, it.imageMedium, it.imageXl)
            val user = User(it.userName, it.firstName, image)
            previousUsers.add(user)
        }

        return previousUsers
    }
}