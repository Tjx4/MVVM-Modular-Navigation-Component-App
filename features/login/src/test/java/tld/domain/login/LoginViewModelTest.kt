package tld.domain.login

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.repositories.authentication.AuthenticationRepository
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


       // Mockito.`when`(conversionViewModel.fXRepository.getConversion(API_KEY, from, to, amount)).thenReturn(conversion)
       // conversionViewModel.convertCurrency(from, to, amount)

        //assertEquals(conversionViewModel.dialogErrorMessage.value, error.info)
    }

    @Test
    fun `check if response error message is displayed when call unsuccessful`() = runBlockingTest {

    }

    @Test
    fun `test if user is set on login success`() = runBlockingTest {

    }

}