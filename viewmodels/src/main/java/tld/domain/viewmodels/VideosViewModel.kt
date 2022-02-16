package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.domain.myapplication.constants.OUTLETS_PAGE_SIZE
import com.domain.myapplication.models.Image
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tld.domain.viewmodels.pagingSaurce.ItemPagingSource

class VideosViewModel(application: Application, val itemsRepository: ItemsRepository) : AndroidViewModel(application){

    private var _itemImage: MutableLiveData<Image> = MutableLiveData()
    val itemImage: MutableLiveData<Image>
        get() = _itemImage

    val items = Pager(config = PagingConfig(pageSize = OUTLETS_PAGE_SIZE)) {
        ItemPagingSource(itemsRepository)
    }.flow.cachedIn(viewModelScope)

   suspend fun getItemImage(url: String){
        val itemImage = itemsRepository.getItemImage(url)

       withContext(Dispatchers.Main){
           when(itemImage){
                null -> {}
               else -> _itemImage.value = itemImage
           }
       }
    }

    fun updateItem(pair: HashMap<Image, Int>){

    }
}