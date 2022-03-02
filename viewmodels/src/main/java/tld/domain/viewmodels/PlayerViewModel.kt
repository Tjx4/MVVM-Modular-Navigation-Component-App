package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.base.viewModel.BaseViewModel
import com.domain.repositories.authentication.AuthenticationRepository

class PlayerViewModel(application: Application, val authenticationRepository: AuthenticationRepository) : BaseViewModel(application){

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean>
        get() = _isLoading
}