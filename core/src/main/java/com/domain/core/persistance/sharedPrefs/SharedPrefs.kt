package com.domain.core.persistance.sharedPrefs

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SharedPrefs(private val application: Application) {
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(application.packageName + "_preferences", Context.MODE_PRIVATE)
    private val FIRSTTIME = "firstTime"
    private val FAV_ITEMS = "favourite_items"

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


    var favItems: List<String>?
        get() {
            val json = sharedPreferences.getString(FAV_ITEMS, "")
            val type: Type = object : TypeToken<ArrayList<String>?>() {}.type
            return Gson().fromJson(json, type) ?: ArrayList()
        }
        set(cartOutlets) {
            val editor = sharedPreferences.edit()
            val connectionsJSONString = Gson().toJson(cartOutlets)
            editor.putString(FAV_ITEMS, connectionsJSONString)
            editor.commit()
        }

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