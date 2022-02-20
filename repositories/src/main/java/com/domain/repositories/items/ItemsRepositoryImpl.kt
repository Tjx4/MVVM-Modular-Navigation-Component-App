package com.domain.repositories.items

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.core.persistance.room.tables.favItems.FavItemsTable
import com.domain.myapplication.constants.API_KEY
import com.domain.myapplication.extensions.getUniqueString
import com.domain.myapplication.models.DBOperation
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.Image

class ItemsRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: MySqliteDB) : ItemsRepository {

    override suspend fun getRemoteItems(): List<Item>? {
        return try {
            retrofitServices.getItems(API_KEY)
        }
        catch (ex: Exception) {
            //firebaseCrashlytics.recordException(ex)
            null
        }
    }

    override suspend fun getItemImage(url: String): Image? {
        return try {
            retrofitServices.getItemImage(url)
        }
        catch (ex: Exception) {
            //firebaseCrashlytics.recordException(ex)
            null
        }
    }

    override suspend fun getFavourites(): List<Item>? {
        val favItems = ArrayList<Item>()

        val favItemTables = database.favItemsDAO.getAllItems()
        favItemTables?.forEach {
            val image = Image(it.imageThumbNail, it.imageMedium, it.imageXl)
            val favItem = Item(it.itemId, it.itemName, image, "", true)
            favItems.add(favItem)
        }

        return favItems
    }

    override suspend fun saveItemFavourites(item: Item): DBOperation {
        return try {
            val favItemTable = FavItemsTable(itemId = item.id, itemName = item.itemName, imageThumbNail = item.image?.thumbNail,  imageMedium = item.image?.medium, imageXl = item.image?.xl)
            database.favItemsDAO.insert(favItemTable)
            DBOperation(true)
        }
        catch (ex: Exception){
            DBOperation(false, "$ex")
        }
    }

    override suspend fun saveItemsToFavourites(favourites: List<Item>): DBOperation {
        return try {
            val favouriteTables = ArrayList<FavItemsTable>()
            favourites?.forEach {
                val favItemTable = FavItemsTable(itemId = it.id, itemName = it.itemName, imageThumbNail = it.image?.thumbNail,  imageMedium = it.image?.medium, imageXl = it.image?.xl)
                favouriteTables.add(favItemTable)
            }

            database.favItemsDAO.insertAll(favouriteTables)
            DBOperation(true)
        }
        catch (ex: Exception){
            DBOperation(false, "$ex")
        }
    }

    override suspend fun removeItemFromFavourites(item: Item): DBOperation {
        return try {
            val favItemTable = FavItemsTable(itemId = item.id, itemName = item.itemName, imageThumbNail = item.image?.thumbNail,  imageMedium = item.image?.medium, imageXl = item.image?.xl)
            database.favItemsDAO.delete(favItemTable)
            DBOperation(true)
        }
        catch (ex: Exception){
            DBOperation(false, "$ex")
        }
    }

    override suspend fun clearItems(): DBOperation {
        return try {
            database.favItemsDAO.clear()
            DBOperation(true)
        }
        catch (ex: Exception){
            DBOperation(false, "$ex")
        }
    }

}