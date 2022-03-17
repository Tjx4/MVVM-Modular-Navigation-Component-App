package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ItemsViewModel(application: Application, val itemsRepo: ItemsRepository) : AndroidViewModel(application) {

    protected var _currentItem: MutableLiveData<Int> = MutableLiveData()
    val currentItem: MutableLiveData<Int>
        get() = _currentItem

    protected var _favItem: MutableLiveData<Item> = MutableLiveData()
    val favItem: MutableLiveData<Item>
        get() = _favItem

    fun checkAndFetchImage(item: Item, position: Int){
        if(item.image != null || item.links?.get(0)?.href.isNullOrEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            getItemImage(item, position)
        }
    }
    
    suspend fun getItemImage(item: Item, position: Int){
        item.links?.get(0)?.href?.let { url ->
            val itemImage = itemsRepo.getItemImage(url)

            withContext(Dispatchers.Main){
                when(itemImage){
                    null -> {}
                    else -> {
                        item.image = itemImage
                        _currentItem.value = position
                    }
                }
            }
        }
    }

    fun toggleFavItem(item: Item, position: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val favItems = itemsRepo.getFavourites()
            val ifFav =  favItems?.any{  it.id == item.id }

            when(ifFav){
                true -> removeItemFromFavourites(item, position)
                else -> addItemToFavourites(item, position)
            }
        }
    }

    suspend fun addItemToFavourites(item: Item, position: Int){
        val addItem = itemsRepo.saveItemFavourites(item)

        withContext(Dispatchers.Main) {
            when (addItem.isSuccessful) {
                true -> {
                    item.isFav = true
                    _currentItem.value = position
                    _favItem.value = item
                }
                else -> { /* Show error */ }
            }
        }
    }

    suspend fun removeItemFromFavourites(item: Item, position: Int){
        val removeItem = itemsRepo.removeItemFromFavourites(item)

        withContext(Dispatchers.Main) {
            when (removeItem.isSuccessful) {
                true -> {
                    item.isFav = false
                    _currentItem.value = position
                }
                else -> { /* Show error */
                }
            }
        }

    }

}