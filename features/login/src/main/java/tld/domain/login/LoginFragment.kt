package tld.domain.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.base.BaseFragment
import com.domain.myapplication.models.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.login.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        loginViewModel.currentUser.observe(viewLifecycleOwner, { onUserSet(it) })
        loginViewModel.errorMessage.observe(viewLifecycleOwner, { onLoginError(it) })
        loginViewModel.isValidInput.observe(viewLifecycleOwner, { onValidationComplete() })
    }

    private fun onUserSet(user: User){
        drawerController.navigateFromLoginToDashboard()
    }

    private fun onLoginError(message: String){

    }

    private fun onValidationComplete(){
        loginViewModel.viewModelScope.launch (Dispatchers.IO){
            loginViewModel.loginUser(loginViewModel.username.value!!, loginViewModel.password.value!!)
        }
    }
}