package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.domain.myapplication.constants.OUTLETS_PAGE_SIZE
import com.domain.repositories.items.ItemsRepository
import tld.domain.viewmodels.pagingSaurce.ItemPagingSource

class VideosViewModel(application: Application, itemsRepository: ItemsRepository) : AndroidViewModel(application){

    val items = Pager(config = PagingConfig(pageSize = OUTLETS_PAGE_SIZE)) {
        ItemPagingSource(itemsRepository)
    }.flow.cachedIn(viewModelScope)
}