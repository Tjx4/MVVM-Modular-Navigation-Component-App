package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.extensions.isValidEmailORMobile
import com.domain.myapplication.extensions.isValidPassword
import com.domain.myapplication.models.User
import com.domain.repositories.authentication.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val app: Application, val authenticationRepository: AuthenticationRepository) : AndroidViewModel(app){

    private val _username: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String>
        get() = _username

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String>
        get() = _password

    private val _usernameErrorMessage: MutableLiveData<String> = MutableLiveData()
    val usernameErrorMessage: MutableLiveData<String>
        get() = _usernameErrorMessage

    private val _passwordErrorMessage: MutableLiveData<String> = MutableLiveData()
    val passwordErrorMessage: MutableLiveData<String>
        get() = _passwordErrorMessage

    private val _loginErrorMessage: MutableLiveData<String> = MutableLiveData()
    val loginErrorMessage: MutableLiveData<String>
        get() = _loginErrorMessage

    private val _isValidInput: MutableLiveData<Boolean> = MutableLiveData()
    val isValidInput: MutableLiveData<Boolean>
        get() = _isValidInput

    private val _currentUser: MutableLiveData<User> = MutableLiveData()
    val currentUser: MutableLiveData<User>
        get() = _currentUser

    init {
        _username.value = "email@domain.com"
        _password.value = "Pl@12345"
    }

    fun attemptLogin(){
        validateDetails(_username.value ?: "", _password.value ?: "")
    }

    fun validateDetails(username: String, password: String){
        when {
            !username.isValidEmailORMobile() -> _usernameErrorMessage.value = app.getString(R.string.invalid_username)
            !password.isValidPassword() -> _passwordErrorMessage.value = app.getString(R.string.invalid_password)
            else -> _isValidInput.value = true
        }
    }

    suspend fun loginUser(username: String, password: String){
        val login = authenticationRepository.loginUser(username, password)

        withContext(Dispatchers.Main) {
            when {
                login == null -> _loginErrorMessage.value = app.getString(R.string.login_error_message)
                login?.error != null -> _loginErrorMessage.value = login?.error?.message
                else -> _currentUser.value = login.user
            }
        }

    }
}