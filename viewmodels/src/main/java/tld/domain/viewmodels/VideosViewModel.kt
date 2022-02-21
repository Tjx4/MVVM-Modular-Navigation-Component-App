package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.domain.myapplication.R
import com.domain.myapplication.constants.PAGE_SIZE
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tld.domain.viewmodels.pagingSaurce.ItemPagingSource

class VideosViewModel(application: Application, val itemsRepository: ItemsRepository) : AndroidViewModel(application){
    private var _currentItem: MutableLiveData<Int> = MutableLiveData()
    val currentItem: MutableLiveData<Int>
        get() = _currentItem

    private var _isIntiItems: MutableLiveData<Boolean> = MutableLiveData()
    val isIntiItems: MutableLiveData<Boolean>
        get() = _isIntiItems

    private var _favItem: MutableLiveData<Item> = MutableLiveData()
    val favItem: MutableLiveData<Item>
        get() = _favItem

    val items = Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
        ItemPagingSource(itemsRepository)
    }.flow.cachedIn(viewModelScope)

    fun checkAndFetchImage(item: Item, position: Int){
        if(item.image != null || item.metaData.isNullOrEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            getItemImage(item, position)
        }
    }

    suspend fun getItemImage(item: Item, position: Int){
        item.metaData?.let { url ->
            val itemImage = itemsRepository.getItemImage(url)

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

    fun checkAndShowFav(item: Item, position: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val favItems = itemsRepository.getFavourites()
            val ifFav =  favItems?.any{  it.id == item.id }

            withContext(Dispatchers.Main) {
                when(ifFav){
                    true -> {
                        item.isFav = true
                        _currentItem.value = position
                    }
                    else -> { /* do something */ }
                }
            }

        }
    }

    fun iniFav(items: List<Item>){
        viewModelScope.launch(Dispatchers.IO) {
           val favItems = itemsRepository.getFavourites()
            favItems?.forEach { item ->
                item.isFav = favItems?.any{ it.id == item.id }
            }

            withContext(Dispatchers.Main) {
                _isIntiItems.value = true
            }
        }
    }

    fun toggleFavItem(item: Item, position: Int){
        viewModelScope.launch(Dispatchers.IO) {
           val favItems = itemsRepository.getFavourites()
            val ifFav =  favItems?.any{  it.id == item.id }

            when(ifFav){
                true -> removeItemFromFavourites(item, position)
                else -> addItemToFavourites(item, position)
            }
        }
    }

    suspend fun addItemToFavourites(item: Item, position: Int){
        val addItem = itemsRepository.saveItemFavourites(item)

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
        val removeItem = itemsRepository.removeItemFromFavourites(item)

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