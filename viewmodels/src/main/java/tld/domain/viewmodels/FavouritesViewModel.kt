package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.Image
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(application: Application, private val itemsRepository: ItemsRepository) : AndroidViewModel(application) {
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _Items: MutableLiveData<List<Item>> = MutableLiveData()
    val items: MutableLiveData<List<Item>>
        get() = _Items

    private val _noItems: MutableLiveData<Boolean> = MutableLiveData()
    val noItems: MutableLiveData<Boolean>
        get() = _noItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            getUserFavourites()
        }
    }

    suspend fun getUserFavourites() {
        val favourites = itemsRepository.getFavourites()

        withContext(Dispatchers.Main){
            when {
                favourites.isNullOrEmpty() -> _noItems.value = true
                else -> _Items.value = favourites
            }
        }
    }

    suspend fun addItemsToFavourites(items: List<Item>) {
        itemsRepository.saveFavouriteItems(items)
    }

    fun clearItems() {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.clearItems()

            withContext(Dispatchers.Main){
                _noItems.value = true
            }
        }
    }

    fun addFavItems() {
        val item1Image = Image(
            "http://appicsoftware.xyz/demo/images/dstv.jpg",
            "http://appicsoftware.xyz/demo/images/dstv.jpg",
            "http://appicsoftware.xyz/demo/images/dstv.jpg"
        )

        val item2Image = Image(
            "http://appicsoftware.xyz/demo/images/showmax.png",
            "http://appicsoftware.xyz/demo/images/showmax.png",
            "http://appicsoftware.xyz/demo/images/showmax.png"
        )

        val favItems = arrayListOf<Item>(Item("1", "DSTV", item1Image, null), Item("2", "Showmax", item2Image, null))
        viewModelScope.launch(Dispatchers.IO) {
            addItemsToFavourites(favItems)
            getUserFavourites()
        }
    }

    fun addManyItems() {
        val item1Image = Image(
            "http://appicsoftware.xyz/demo/images/dstv.jpg",
            "http://appicsoftware.xyz/demo/images/dstv.jpg",
            "http://appicsoftware.xyz/demo/images/dstv.jpg"
        )

        val item2Image = Image(
            "http://appicsoftware.xyz/demo/images/showmax.png",
            "http://appicsoftware.xyz/demo/images/showmax.png",
            "http://appicsoftware.xyz/demo/images/showmax.png"
        )

        val favItems = arrayListOf<Item>(
            Item("", "DSTV", item1Image, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Item", null, null),
            Item("", "Showmax", item2Image, null)
        )

        viewModelScope.launch(Dispatchers.IO) {
            addItemsToFavourites(favItems)
            getUserFavourites()
        }
    }

}