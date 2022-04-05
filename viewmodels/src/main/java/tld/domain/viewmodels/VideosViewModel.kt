package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.domain.myapplication.constants.ITEM_PAGE_SIZE
import com.domain.myapplication.enums.Links
import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tld.domain.viewmodels.pagingSaurce.ItemPagingSource

class VideosViewModel(application: Application, val itemsRepository: ItemsRepository) : ItemsViewModel(application, itemsRepository){

    val items = Pager(config = PagingConfig(pageSize = ITEM_PAGE_SIZE)) {
        ItemPagingSource(itemsRepository)
    }.flow.cachedIn(viewModelScope)

/*
Todo remove

    fun checkAndFetchCategoryImage2(items: List<Item>){
        viewModelScope.launch(Dispatchers.IO) {
            var index = 0
            for(item in items) {
                if(item.image != null || item.links?.get(Links.CardInfo.index)?.href.isNullOrEmpty()) continue


                    var itemImage: Image? = null
                    async {
                        item.links?.get(Links.CardInfo.index)?.href?.let { url ->
                            itemImage = itemsRepo.getItemImage(url)
                        }
                    }.await()

                    withContext(Dispatchers.Main) {
                        item.image = itemImage
                        _currentItem.value = index
                        index++
                }
            }

        }

        val done = true
    }
*/

}