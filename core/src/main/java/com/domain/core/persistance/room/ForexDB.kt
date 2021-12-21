package com.domain.core.persistance.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.domain.core.persistance.room.tables.pairHistory.PairHistoryDAO
import com.domain.core.persistance.room.tables.pairHistory.PairHistoryTable

@Database(entities = [PairHistoryTable::class], version = 1, exportSchema = false)
abstract class ForexDB : RoomDatabase() {
    abstract val pairHistoryDAO: PairHistoryDAO

    companion object{
        @Volatile
        private var INSTANCE: ForexDB? = null

        fun getInstance(context: Context): ForexDB {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, ForexDB::class.java, "forex_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return  instance
            }
        }
    }
}