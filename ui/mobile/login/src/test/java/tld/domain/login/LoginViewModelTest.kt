package tld.domain.login

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.LoginResponse
import com.domain.myapplication.models.User
import com.domain.repositories.authentication.AuthenticationRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations.openMocks
import tld.domain.viewmodels.LoginViewModel

class LoginViewModelTest {
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    private lateinit var mockApplication: Application
    @Mock
    private lateinit var authenticationRepository: AuthenticationRepository

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        openMocks(this)
        loginViewModel = LoginViewModel(mockApplication, authenticationRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `default error message should be set on call return null`() = runBlockingTest {
        val username = "email@domain.com"
        val password = "P@12345"
        val expectedErrorMessage = "Error login in please check your details"

        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(null)
        Mockito.`when`(mockApplication.getString(R.string.login_error_message)).thenReturn(expectedErrorMessage)
        loginViewModel.loginUser(username, password)

        assertEquals(expectedErrorMessage, loginViewModel.loginErrorMessage.value)
    }

    @Test
    fun `response error message should be set on call fail`() = runBlockingTest {
        val username = "email@domain.com"
        val password = "P@12345"
        val expectedErrorMessage = "Incorrect password"

        val mockResponse = LoginResponse(null, ErrorResponse(expectedErrorMessage))
        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(mockResponse)
        loginViewModel.loginUser(username, password)

        assertEquals(expectedErrorMessage, loginViewModel.loginErrorMessage.value)
    }

    @Test
    fun `is login success should set current user`() = runBlockingTest {
        val username = "email@domain.com"
        val password = "P@12345"
        val user = User(username, "")

        val mockResponse = LoginResponse(user, null)
        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(mockResponse)
        loginViewModel.loginUser(username, password)

        assertEquals(user, loginViewModel.currentUser.value)
    }

}