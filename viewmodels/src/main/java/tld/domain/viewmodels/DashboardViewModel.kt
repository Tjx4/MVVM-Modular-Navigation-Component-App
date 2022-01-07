package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.core.persistance.sharedPrefs.SharedPrefs
import com.domain.repositories.weatherRepository.WeatherRepository

class DashboardViewModel(application: Application, private val weatherRepository: WeatherRepository, private val sharedPrefs: SharedPrefs) : AndroidViewModel(application){
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _logout: MutableLiveData<Boolean> = MutableLiveData()
    val logout: MutableLiveData<Boolean>
        get() = _logout

    fun logOutUser(){
        sharedPrefs.currentUser = null
        _logout.value = true
    }

}