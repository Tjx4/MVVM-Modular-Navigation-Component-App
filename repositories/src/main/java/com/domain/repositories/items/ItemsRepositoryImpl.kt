package com.domain.repositories.items

import com.domain.core.networking.retrofit.RetrofitServices
import com.domain.core.persistance.room.MySqliteDB
import com.domain.core.persistance.room.tables.favItems.FavItemsTable
import com.domain.myapplication.constants.API_KEY
import com.domain.myapplication.models.*

class ItemsRepositoryImpl(private val retrofitServices: RetrofitServices, private val database: MySqliteDB) : ItemsRepository {

    override suspend fun getCategorizedItems(): List<ItemCategory>? {
        return try {
            retrofitServices.getDashBoard(API_KEY)
        }
        catch (ex: Exception) {
            //firebaseCrashlytics.recordException(ex)
            null
        }
    }

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

    override suspend fun getItemVideo(id: String): APIResponse  {
        return try {
            val video = retrofitServices.getItemVideo(API_KEY, id)
            APIResponse(video)
        }
        catch (ex: Exception) {
            //firebaseCrashlytics.recordException(ex)
            APIResponse(null, ex.message)
        }
    }

    override suspend fun refreshList(url: String): ItemCategory? {
        return try {
            retrofitServices.getItemCategory(url)
        }
        catch (ex: Exception) {
            //firebaseCrashlytics.recordException(ex)
            null
        }
    }

    override suspend fun getFavourites(): List<Item>? {
        val favItems = ArrayList<Item>()
        database.favItemsDAO.getAllItems()?.forEach {
            val favItem = getFavItemFromFavTable(it)
            favItems.add(favItem)
        }

        return favItems
    }

    override suspend fun saveItemFavourites(item: Item): DBOperation {
        return try {
            val favItemTable = getFavTableFromItem(item)
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
                val favItemTable = getFavTableFromItem(it)
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
            val favItemTable = getFavTableFromItem(item)
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

    //Todo consider moving to extensions
    private fun getFavTableFromItem(item: Item) = FavItemsTable(
            "${item.id}",
            item.itemName,
            item.image?.thumbNail,
            item.image?.medium,
            item.image?.xl,
        /*
            item.links?.get(0)?.rel,
            item.links?.get(0)?.method,
            item.links?.get(0)?.href,
            item.links?.get(1)?.rel,
            item.links?.get(1)?.method,
            item.links?.get(1)?.href,
            item.links?.get(2)?.rel,
            item.links?.get(2)?.method,
            item.links?.get(2)?.href
    */
        )

    private fun getFavItemFromFavTable(favItemsTable: FavItemsTable) : Item {
        val image = Image(favItemsTable.imageThumbNail, favItemsTable.imageMedium, favItemsTable.imageXl)
/*
        val links = arrayListOf(
            Link(favItemsTable.cardInfoRel, favItemsTable.cardInfoMethod, favItemsTable.cardInfoHref),
            Link(favItemsTable.updateRel, favItemsTable.updateMethod, favItemsTable.updateHref),
            Link(favItemsTable.deleteRel, favItemsTable.deleteMethod, favItemsTable.deleteHref)
        )
*/
        return Item(favItemsTable.id, favItemsTable.itemName, image, null, true)
    }

}