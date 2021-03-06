package com.domain.core.persistance.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.domain.core.persistance.room.tables.favItems.FavItemsDAO
import com.domain.core.persistance.room.tables.favItems.FavItemsTable
import com.domain.core.persistance.room.tables.users.UsersDAO
import com.domain.core.persistance.room.tables.users.UsersTable

@Database(entities = [UsersTable::class, FavItemsTable::class], version = 1, exportSchema = false)
abstract class MySqliteDB : RoomDatabase() {
    abstract val usersDAO: UsersDAO
    abstract val favItemsDAO: FavItemsDAO

    companion object{
        @Volatile
        private var INSTANCE: MySqliteDB? = null

        fun getInstance(context: Context): MySqliteDB {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, MySqliteDB::class.java, "my_sqlite_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return  instance
            }
        }
    }
}