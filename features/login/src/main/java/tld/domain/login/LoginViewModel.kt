package tld.domain.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.models.User
import com.domain.repositories.authentication.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val app: Application, private val authenticationRepository: AuthenticationRepository) : AndroidViewModel(app){

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String>
        get() = _username

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String>
        get() = _password

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: MutableLiveData<String>
        get() = _errorMessage

    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    val currentUser: MutableLiveData<User>
        get() = _currentUser

    suspend fun loginUser(username: String, password: String){


    }
}