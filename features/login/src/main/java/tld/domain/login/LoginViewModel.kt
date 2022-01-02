package tld.domain.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.extensions.isValidPassword
import com.domain.myapplication.extensions.isValidUsername
import com.domain.myapplication.models.User
import com.domain.repositories.authentication.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val app: Application, val authenticationRepository: AuthenticationRepository) : AndroidViewModel(app){

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String>
        get() = _username

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String>
        get() = _password

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: MutableLiveData<String>
        get() = _errorMessage

    private val _isValidInput: MutableLiveData<Boolean> = MutableLiveData()
    val isValidInput: MutableLiveData<Boolean>
        get() = _isValidInput

    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    val currentUser: MutableLiveData<User>
        get() = _currentUser

    init {
        _username.value = "email@domain.com"
        _password.value = "P@12345"
    }

    fun attemptLogin(){
        val username = _username.value
        val password = _password.value

        when{
            username?.isValidUsername() != true -> _errorMessage.value  = app.getString(R.string.invalid_username)
            password?.isValidPassword() != true -> _errorMessage.value  = app.getString(R.string.invalid_password)
            else -> _isValidInput.value = true
        }
    }

    suspend fun loginUser(username: String, password: String){
        val login = authenticationRepository.loginUser(username, password)

        withContext(Dispatchers.Main) {
            when {
                login == null -> _errorMessage.value = app.getString(R.string.login_error_message)
                login?.error != null -> _errorMessage.value = login?.error?.message
                else -> _currentUser.value = login.user
            }
        }

    }

    fun isValidUsername() {

    }

    fun isValidEmail() {

    }
}