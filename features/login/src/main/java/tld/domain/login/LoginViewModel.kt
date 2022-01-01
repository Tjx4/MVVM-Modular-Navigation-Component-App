package tld.domain.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.repositories.authentication.AuthenticationRepository

class LoginViewModel(application: Application, authenticationRepository: AuthenticationRepository) : AndroidViewModel(application){

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String>
        get() = _username

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String>
        get() = _password
}