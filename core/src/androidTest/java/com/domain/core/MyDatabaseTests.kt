package com.domain.core

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.domain.core.persistance.room.MySqliteDB
import com.domain.core.persistance.room.tables.favItems.FavItemsDAO
import com.domain.core.persistance.room.tables.favItems.FavItemsTable
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MyDatabaseTests{
    private lateinit var favItemsDAO: FavItemsDAO
    private lateinit var mySqliteDB: MySqliteDB

    @Before
    fun createDB(){
        var context = InstrumentationRegistry.getInstrumentation().targetContext

        mySqliteDB = Room.inMemoryDatabaseBuilder(context, MySqliteDB::class.java)
            .allowMainThreadQueries()
            .build()

        favItemsDAO = mySqliteDB.favItemsDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        mySqliteDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun is_fav_item_successfully_added_to_db(){
        var expectedFavItemsTable = FavItemsTable(1, "", "", "", "")

        favItemsDAO.insert(expectedFavItemsTable)
        val actualFavItemsTableTable = favItemsDAO.get(expectedFavItemsTable.id)

        assertEquals(expectedFavItemsTable.id, actualFavItemsTableTable?.id)
    }

}