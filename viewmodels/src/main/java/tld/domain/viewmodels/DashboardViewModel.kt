package tld.domain.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.work.*
import com.domain.myapplication.base.viewModel.BaseViewModel
import com.domain.myapplication.constants.CATEGORY_PAGE_SIZE
import com.domain.myapplication.constants.P_WORK
import com.domain.myapplication.enums.Links
import com.domain.myapplication.models.Image
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.ItemCategory
import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.*
import tld.domain.viewmodels.pagingSaurce.ItemCategoryPagingSource
import java.util.concurrent.TimeUnit

class DashboardViewModel(application: Application, val authenticationRepository: AuthenticationRepository, val itemsRepository: ItemsRepository) : ItemsViewModel(application, itemsRepository){
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private var _currentCategoryAndItem: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val currentCategoryAndItem: MutableLiveData<Pair<Int, Int>>
        get() = _currentCategoryAndItem

    private val _updatedItemCategory: MutableLiveData<Pair<ItemCategory, Int>> = MutableLiveData()
    val updatedItemCategory: MutableLiveData<Pair<ItemCategory, Int>>
        get() = _updatedItemCategory

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

    fun checkAndFetchCategoryImage(item: Item, categoryPosition: Int, itemPosition: Int) {
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
val startedWorkers = ArrayList<Int>()
    suspend fun startUpdateWorker(itemCategory: ItemCategory, position: Int) {
        val isAlreayStarted = startedWorkers.any{ it == position }
        if(isAlreayStarted) return

        val refreshInterval = itemCategory.timeToRefreshInSeconds
        val url = itemCategory.links?.get(0)?.href

        when {
            refreshInterval == null -> { /* handle url error */ }
            url == null -> { /* handle url error */ }
            else -> {
                /*
                val periodicWork = PeriodicWorkRequestBuilder<RefreshWorker>(
                    refreshInterval.toLong(),
                    TimeUnit.SECONDS,
                    15,
                    TimeUnit.SECONDS
                ).addTag("MY_WORKER")

                val myWork = periodicWork.build()
                */
                val inputData = Data.Builder().putInt("", position).build()
                val constraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()

                val periodicWork = PeriodicWorkRequest.Builder(
                    RefreshWorker::class.java,
                    refreshInterval.toLong(),
                    TimeUnit.SECONDS
                ).setConstraints(constraints)
                    .addTag(P_WORK)
                    .setInputData(inputData)
                    .setInitialDelay(refreshInterval.toLong(), TimeUnit.SECONDS)
                    .build()

                WorkManager.getInstance(app).enqueueUniquePeriodicWork("MY_WORKER", ExistingPeriodicWorkPolicy.REPLACE, periodicWork)
startedWorkers.add(position)
            }
        }
    }

    class RefreshWorker(context: Context,  val params: WorkerParameters, val dashboardViewModel: DashboardViewModel) : Worker(context, params) {

        override fun doWork(): Result {
            val url = ""
            val position = 0
           // dashboardViewModel.updateList(url, position)
            return Result.success()
        }
    }

    suspend fun updateList(url: String, position: Int) {
        val newList = itemsRepository.refreshList(url)

        withContext(Dispatchers.Main) {
            when (newList){
                null -> { /* handle error */ }
                else -> {
                    _updatedItemCategory.value = Pair(newList, position)
                }
            }
        }
    }

    suspend fun getCardInfo(url: String, categoryPosition: Int, itemPosition: Int) {
        val cardInfo = itemsRepository.getCardInfo(url)

        withContext(Dispatchers.Main) {
            when (cardInfo){
                null -> { /* handle error */ }
                else -> { /* Update live data */ }
            }
        }
    }

    suspend fun addItem(url: String, categoryPosition: Int, itemPosition: Int) {
        val update = itemsRepository.addItem(url)

        withContext(Dispatchers.Main) {
            when (update){
                null -> { /* handle error */ }
                else -> { /* Update live data */ }
            }
        }
    }

    suspend fun deleteItem(url: String, categoryPosition: Int, itemPosition: Int) {
        val delete = itemsRepository.deleteItem(url)

        withContext(Dispatchers.Main) {
            when (delete){
                null -> { /* handle error */ }
                else -> { /* Update live data */ }
            }
        }
    }

    suspend fun logOutUser(){
        authenticationRepository.logOutUser()
        withContext(Dispatchers.Main) {
            _logout.value = true
        }
    }

}