package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.base.viewModel.BaseViewModel
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(application: Application, private val itemsRepository: ItemsRepository) : BaseViewModel(application) {
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _items: MutableLiveData<List<Item>> = MutableLiveData()
    val items: MutableLiveData<List<Item>>
        get() = _items

    private val _noItems: MutableLiveData<Boolean> = MutableLiveData()
    val noItems: MutableLiveData<Boolean>
        get() = _noItems

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFavourites()
        }
    }

    suspend fun getFavourites() {
        val favourites = itemsRepository.getFavourites()

        withContext(Dispatchers.Main){
            when {
                favourites.isNullOrEmpty() -> _noItems.value = true
                else -> _items.value = favourites
            }
        }
    }

    fun clearItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val clearItems = itemsRepository.clearItems()

            withContext(Dispatchers.Main) {
                when (clearItems.isSuccessful) {
                    true -> _noItems.value = true
                    else -> { /*Handle Db error */
                    }
                }
            }
        }
    }

}