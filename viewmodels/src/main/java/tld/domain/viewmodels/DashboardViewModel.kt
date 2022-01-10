package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.repositories.authentication.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application, private val authenticationRepository: AuthenticationRepository) : AndroidViewModel(application){
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _logout: MutableLiveData<Boolean> = MutableLiveData()
    val logout: MutableLiveData<Boolean>
        get() = _logout

    fun logOutUser(){
        viewModelScope.launch (Dispatchers.IO){
            authenticationRepository.logOutUser()
            withContext(Dispatchers.Main) {
                _logout.value = true
            }
        }
    }

}