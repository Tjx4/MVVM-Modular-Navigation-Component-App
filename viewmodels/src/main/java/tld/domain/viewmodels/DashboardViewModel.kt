package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.repositories.weatherRepository.WeatherRepository

class DashboardViewModel(application: Application, private val weatherRepository: WeatherRepository) : AndroidViewModel(application){
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading
}