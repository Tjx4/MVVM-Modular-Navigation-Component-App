package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.base.viewModel.BaseViewModel
import com.domain.myapplication.models.ItemType
import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application, val authenticationRepository: AuthenticationRepository, val itemsRepository: ItemsRepository) : BaseViewModel(application){
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _errorFetchingItems: MutableLiveData<Boolean> = MutableLiveData()
    val errorFetchingItems: MutableLiveData<Boolean>
        get() = _errorFetchingItems

    private val _types: MutableLiveData<List<ItemType>> = MutableLiveData()
    val types: MutableLiveData<List<ItemType>>
        get() = _types

    private val _logout: MutableLiveData<Boolean> = MutableLiveData()
    val logout: MutableLiveData<Boolean>
        get() = _logout

    suspend fun initDashboard(){
        val types = itemsRepository.getCategorizedItems()
        withContext(Dispatchers.Main) {
            when(types.isNullOrEmpty()){
                true -> _errorFetchingItems.value = true
                else -> _types.value = types
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