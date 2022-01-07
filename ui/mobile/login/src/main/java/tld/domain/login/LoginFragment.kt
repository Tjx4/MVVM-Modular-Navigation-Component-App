package tld.domain.login

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.domain.core.persistance.sharedPrefs.SharedPrefs
import com.domain.myapplication.base.BaseFragment
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.models.User
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.login.databinding.FragmentLoginBinding
import tld.domain.viewmodels.LoginViewModel

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
        loginViewModel.skipLogin.observe(viewLifecycleOwner, { onSkipLogin() })
        loginViewModel.currentUser.observe(viewLifecycleOwner, { onUserSet(it) })
        loginViewModel.usernameErrorMessage.observe(viewLifecycleOwner, { onInvalidUsername(it) })
        loginViewModel.passwordErrorMessage.observe(viewLifecycleOwner, { onInvalidPassword(it) })
        loginViewModel.loginErrorMessage.observe(viewLifecycleOwner, { onLoginError(it) })
        loginViewModel.isValidInput.observe(viewLifecycleOwner, { onValidationComplete() })
    }

    override fun onResume() {
        super.onResume()
        drawerController.hideBottomNav()
    }

    override fun onBackPressed() {
        activity?.moveTaskToBack(true)
    }

    private fun onSkipLogin(){
        drawerController.navigateFromLoginToDashboard()
    }

    private fun onUserSet(user: User){
        drawerController.navigateFromLoginToDashboard()
    }

    private fun onInvalidUsername(message: String){
        tvUsernameError.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0)
        tvUsernameError.visibility = View.VISIBLE
        tvError.visibility = View.INVISIBLE
    }

    private fun onInvalidPassword(message: String){
        tvPasswordError.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0)
        tvPasswordError.visibility = View.VISIBLE
        tvUsernameError.visibility = View.INVISIBLE
        tvError.visibility = View.INVISIBLE
    }

    private fun onLoginError(message: String){
        tvError.visibility = View.VISIBLE
        tvUsernameError.visibility = View.INVISIBLE
        tvPasswordError.visibility = View.INVISIBLE
    }

    private fun onValidationComplete(){
        loginViewModel.viewModelScope.launch (Dispatchers.IO){
            loginViewModel.loginUser(loginViewModel.username.value!!, loginViewModel.password.value!!)
        }
    }
}