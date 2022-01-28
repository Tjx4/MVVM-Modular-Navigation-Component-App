package tld.domain.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.myapplication.models.ErrorResponse
import com.domain.myapplication.models.Image
import com.domain.myapplication.models.LoginResponse
import com.domain.myapplication.models.User
import com.domain.repositories.authentication.AuthenticationRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
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
import android.R.attr.password

import java.util.concurrent.ThreadLocalRandom.current
import java.util.regex.Matcher
import java.util.regex.Pattern


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
        val username = "email@domain.tld"
        val password = "P@12345"
        val expectedErrorMessage = "Error login in please check your details"

        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(null)
        Mockito.`when`(mockApplication.getString(R.string.login_error_message)).thenReturn(expectedErrorMessage)
        loginViewModel.loginUser(username, password)

        assertEquals(expectedErrorMessage, loginViewModel.loginErrorMessage.value)
    }

    @Test
    fun `response error message should be set on call fail`() = runBlockingTest {
        val username = "email@domain.tld"
        val password = "P@12345"
        val expectedErrorMessage = "Incorrect password"

        val mockResponse = LoginResponse(null, ErrorResponse(expectedErrorMessage))
        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(mockResponse)
        loginViewModel.loginUser(username, password)

        assertEquals(expectedErrorMessage, loginViewModel.loginErrorMessage.value)
    }

    @Test
    fun `is login success should set current user`() = runBlockingTest {
        val username = "email@domain.tld"
        val password = "P@12345"
        val picture = Image("", "", "")
        val user = User(username, "", picture)

        val mockResponse = LoginResponse(user, null)
        Mockito.`when`(loginViewModel.authenticationRepository.loginUser(username, password)).thenReturn(mockResponse)
        loginViewModel.loginUser(username, password)

        assertEquals(user, loginViewModel.currentUser.value)
    }

    @Test
    fun `is password contain a minimum of 7 characters`() = runBlockingTest {
        val password = "P@12345"

        val actual = loginViewModel.isValidPassword(password)
        val expected = password.length >= 7

        assertEquals(actual, expected)
    }

    @Test
    fun `is password contain a special characters`() = runBlockingTest {
        val password = "P@12345"

        val actual = loginViewModel.isValidPassword(password)
        val pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(password)
        val expected = matcher.find()

        assertEquals(actual, expected)
    }

    @Test
    fun `is password contain a capital letter`() = runBlockingTest {

    }

    @Test
    fun `is password contain a numbers`() = runBlockingTest {

    }

}