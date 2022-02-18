package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.domain.myapplication.constants.PAGE_SIZE
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tld.domain.viewmodels.pagingSaurce.ItemPagingSource

class VideosViewModel(application: Application, val itemsRepository: ItemsRepository) : AndroidViewModel(application){

    private var _currentItem: MutableLiveData<Pair<Item, Int>> = MutableLiveData()
    val currentItem: MutableLiveData<Pair<Item, Int>>
        get() = _currentItem

    val items = Pager(config = PagingConfig(pageSize = PAGE_SIZE)) {
        ItemPagingSource(itemsRepository)
    }.flow.cachedIn(viewModelScope)

    fun checkAndFetchImage(item: Item, position: Int){
        if(item.image != null) return

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
                        _currentItem.value = Pair(item, position)
                    }
                }
            }
        }

    }

}