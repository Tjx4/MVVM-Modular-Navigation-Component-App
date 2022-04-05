package tld.domain.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.work.*
import com.domain.myapplication.constants.CATEGORY_PAGE_SIZE
import com.domain.myapplication.constants.P_WORK
import com.domain.myapplication.constants.P_WORK_POS
import com.domain.myapplication.constants.P_WORK_URL
import com.domain.myapplication.enums.Links
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

    private val _listsError: MutableLiveData<String> = MutableLiveData()
    val listsError: MutableLiveData<String>
        get() = _listsError

    private val _lists: MutableLiveData<List<ItemCategory>> = MutableLiveData()
    val lists: MutableLiveData<List<ItemCategory>>
        get() = _lists

    private var _currentCategoryAndItem: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val currentCategoryAndItem: MutableLiveData<Pair<Int, Int>>
        get() = _currentCategoryAndItem

    private val _enrichedItem: MutableLiveData<Item> = MutableLiveData()
    val enrichedItem: MutableLiveData<Item>
        get() = _enrichedItem

    private val _updatedItemCategory: MutableLiveData<Pair<ItemCategory, Int>> = MutableLiveData()
    val updatedItemCategory: MutableLiveData<Pair<ItemCategory, Int>>
        get() = _updatedItemCategory

    private val _logout: MutableLiveData<Boolean> = MutableLiveData()
    val logout: MutableLiveData<Boolean>
        get() = _logout

    val categories = Pager(config = PagingConfig(pageSize = CATEGORY_PAGE_SIZE)) {
        ItemCategoryPagingSource(_lists.value)
    }.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            initDashboard()
        }
    }

    suspend fun initDashboard(){
        val types = itemsRepository.getCategories()
        withContext(Dispatchers.Main) {
            when(types.isNullOrEmpty()){
                true -> _listsError.value = app.getString(R.string.lists_error)
                else -> _lists.value = types
            }
        }
    }

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

                val inputData = Data.Builder()
                    .putString(P_WORK_URL, url)
                    .putInt(P_WORK_POS, position)
                    .build()

                val constraints = Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()

/*
val periodicWork = PeriodicWorkRequestBuilder<RefreshWorker>(
  refreshInterval.toLong(),
  TimeUnit.SECONDS,
  15,
  TimeUnit.SECONDS
).addTag("MY_WORKER")

val myWork = periodicWork.build()
WorkManager.getInstance(app).enqueueUniquePeriodicWork("MY_WORKER", ExistingPeriodicWorkPolicy.REPLACE, myWork)
*/

                val periodicWork = PeriodicWorkRequest.Builder(
                    RefreshWorker::class.java,
                    20,
                    TimeUnit.SECONDS
                ).setConstraints(constraints)
                    .addTag(P_WORK)
                    .setInputData(inputData)
                    //.setInitialDelay(refreshInterval.toLong(), TimeUnit.SECONDS)
                    .build()

                WorkManager.getInstance(app).enqueueUniquePeriodicWork("MY_WORKER2", ExistingPeriodicWorkPolicy.REPLACE, periodicWork)

startedWorkers.add(position)
            }
        }
    }

    class RefreshWorker(context: Context,  val params: WorkerParameters, val dashboardViewModel: DashboardViewModel) : CoroutineWorker(context, params) {
        override suspend fun doWork(): Result {
            params.inputData.getString(P_WORK_URL)?.let { url ->
                val position = params.inputData.getInt(P_WORK_POS, -1)
                dashboardViewModel.updateList(url, position)

                dashboardViewModel.updatedItemCategory?.value?.first?.let {
                    if (it == null){
                        return Result.failure()
                    }
                }
            }

            return Result.success()
        }
    }

    suspend fun updateList(url: String, position: Int) {
        val newList = itemsRepository.refreshList(url)

        withContext(Dispatchers.Main) {
            when (newList){
                null -> { /* handle error */ }
                else -> _updatedItemCategory.value = Pair(newList, position)
            }
        }
    }

    suspend fun startRecursiveUpdates(itemCategory: ItemCategory, position: Int){
        val isAlreadyStarted = startedWorkers.any{ it == position }
        if(isAlreadyStarted) return

        itemCategory.links?.get(0)?.href?.let { url ->
            startedWorkers.add(position)
            updateList2(url, position, itemCategory)
        }
    }

    suspend fun updateList2(url: String, position: Int, itemCategory: ItemCategory) {
        itemCategory.timeToRefreshInSeconds?.let { refreshInterval ->
            delay((refreshInterval * 10).toLong())

            val newItemCategory = itemsRepository.refreshList(url)
            //newItemCategory?.refreshIndex = newItemCategory?.refreshIndex ?: 0 + 1

            withContext(Dispatchers.Main) {
                when (newItemCategory){
                    null -> { /* handle error */ }
                    else -> {
                        _updatedItemCategory.value = Pair(newItemCategory, position)
                        withContext(Dispatchers.IO) {
                            updateList2(url, position, itemCategory)
                        }
                    }
                }
            }
        }

    }

    suspend fun getCardInfo(item: Item, categoryPosition: Int, itemPosition: Int) {
        val url = item.links?.get(0)?.href ?: ""
        val cardInfo = itemsRepository.getCardInfo(url)

        withContext(Dispatchers.Main) {
            when (cardInfo){
                null -> { /* handle error */ }
                else -> { /* Update live data */ }
            }
        }
    }

    suspend fun addItem(item: Item, categoryPosition: Int, itemPosition: Int) {
        val url = item.links?.get(0)?.href ?: ""
        val update = itemsRepository.addItem(url)

        withContext(Dispatchers.Main) {
            when (update){
                null -> { /* handle error */ }
                else -> { /* Update live data */ }
            }
        }
    }

    suspend fun deleteItem(item: Item, categoryPosition: Int, itemPosition: Int) {
        val url = item.links?.get(0)?.href ?: ""
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