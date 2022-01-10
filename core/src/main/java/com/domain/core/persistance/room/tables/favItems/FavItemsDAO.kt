package com.domain.core.persistance.room.tables.favItems

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavItemsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favItems: FavItemsTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(favItems: List<FavItemsTable>)

    @Update
    fun update(favItemsTable: FavItemsTable)

    @Delete
    fun delete(favItemsTable: FavItemsTable)

    @Query("SELECT * FROM fav_items WHERE id = :key")
    fun get(key: Long): FavItemsTable?

    @Query("SELECT * FROM fav_items ORDER BY id DESC LIMIT 1")
    fun getLastItem(): FavItemsTable?

    @Query("SELECT * FROM fav_items ORDER BY id DESC")
    fun getAllItemsLiveData(): LiveData<List<FavItemsTable>>

    @Query("SELECT * FROM fav_items ORDER BY id DESC")
    fun getAllItems(): List<FavItemsTable>?

    @Query("DELETE  FROM fav_items")
    fun clear()
}