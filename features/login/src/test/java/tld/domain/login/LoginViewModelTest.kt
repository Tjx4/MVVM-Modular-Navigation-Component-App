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
    fun `check if default error message is displayed when response is null`() = runBlockingTest {
        val username = "email@domain.com"
        val password = "P@12345"
        val expectedErrorMessage = "Error login in please check your details" //mockApplication.getString(R.string.login_error_message)

        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(null)
        Mockito.`when`(mockApplication.getString(R.string.login_error_message)).thenReturn(expectedErrorMessage)
        loginViewModel.loginUser(username, password)

        assertEquals(expectedErrorMessage, loginViewModel.errorMessage.value)
    }

    @Test
    fun `check if response error message is displayed when call unsuccessful`() = runBlockingTest {
        val username = "email@domain.com"
        val password = "P@12345"
        val expectedErrorMessage = "Incorrect password"
        val mockResponse = LoginResponse(null, ErrorResponse(expectedErrorMessage))

        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(mockResponse)
        loginViewModel.loginUser(username, password)

        assertEquals(expectedErrorMessage, loginViewModel.errorMessage.value)
    }

    @Test
    fun `test if user is set on login success`() = runBlockingTest {
        val username = "email@domain.com"
        val password = "P@12345"
        val user = User(username, "")
        val mockResponse = LoginResponse(user, null)

        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(mockResponse)
        loginViewModel.loginUser(username, password)

        assertEquals(user, loginViewModel.currentUser.value)
    }

}