package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.domain.myapplication.base.viewModel.BaseViewModel
import com.domain.myapplication.constants.CATEGORY_PAGE_SIZE
import com.domain.myapplication.enums.Links
import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.ItemCategory
import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.*
import tld.domain.viewmodels.pagingSaurce.ItemCategoryPagingSource

class DashboardViewModel(application: Application, val authenticationRepository: AuthenticationRepository, val itemsRepository: ItemsRepository) : ItemsViewModel(application, itemsRepository){
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private var _currentCategoryAndItem: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val currentCategoryAndItem: MutableLiveData<Pair<Int, Int>>
        get() = _currentCategoryAndItem

/*
    private val _errorFetchingItems: MutableLiveData<Boolean> = MutableLiveData()
    val errorFetchingItems: MutableLiveData<Boolean>
        get() = _errorFetchingItems


    private val _types: MutableLiveData<List<ItemCategory>> = MutableLiveData()
    val types: MutableLiveData<List<ItemCategory>>
        get() = _types
*/

    private val _logout: MutableLiveData<Boolean> = MutableLiveData()
    val logout: MutableLiveData<Boolean>
        get() = _logout

    val categories = Pager(config = PagingConfig(pageSize = CATEGORY_PAGE_SIZE)) {
        ItemCategoryPagingSource(itemsRepository)
    }.flow.cachedIn(viewModelScope)

/*
    suspend fun initDashboard(){
        val types = itemsRepository.getCategorizedItems()
        withContext(Dispatchers.Main) {
            when(types.isNullOrEmpty()){
                true -> _errorFetchingItems.value = true
                else -> _types.value = types
            }
        }
    }
*/

    fun checkAndFetchCategoryImage(item: Item, categoryPosition: Int, itemPosition: Int){
        if(item.image != null || item.links?.get(Links.CardInfo.index)?.href.isNullOrEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {

            async {
                getItemImage(item, itemPosition)
            }.await()

            withContext(Dispatchers.Main) {
                _currentCategoryAndItem.value = Pair(categoryPosition, itemPosition)
            }

        }
    }

    //Update workmanager
    suspend fun startUpdateWorker(itemCategory: ItemCategory, position: Int){
        val updateUrl = itemCategory.links?.get(1)?.href ?: return
        val refreshInterval = itemCategory.timeToRefreshInSeconds ?: return

        delay((refreshInterval * 1000).toLong())

        itemsRepository.refreshList(updateUrl)

       // _currentCategoryAndItem.value
    }

    suspend fun logOutUser(){
        authenticationRepository.logOutUser()
        withContext(Dispatchers.Main) {
            _logout.value = true
        }
    }

}