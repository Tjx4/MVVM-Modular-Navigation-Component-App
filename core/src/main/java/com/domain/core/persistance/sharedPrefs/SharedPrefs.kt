package com.domain.core.persistance.sharedPrefs

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPrefs(private val application: Application) {
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(application.packageName + "_preferences", Context.MODE_PRIVATE)
    private val FIRSTTIME = "firstTime"
    private val FAV_CURRENCIES = "user"

    var isFirstTime: Boolean
        get() {
            val json = sharedPreferences.getString(FIRSTTIME, "")
            return Gson().fromJson(json, Boolean::class.java) ?: false
        }
        set(isFirstTime) {
            val editor = sharedPreferences.edit()
            val connectionsJSONString = Gson().toJson(isFirstTime)
            editor.putString(FIRSTTIME, connectionsJSONString)
            editor.commit()
        }


    //Todo getlist of items


    companion object{
        fun getInstance(application: Application): SharedPrefs {
            synchronized(this){
                var instance: SharedPrefs? = null

                if(instance == null){
                    instance = SharedPrefs(application)
                }

                return  instance
            }
        }
    }
}