package tld.domain.viewmodels

import android.app.Application
import android.content.ClipData
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.myapplication.models.*
import com.domain.repositories.authentication.AuthenticationRepository
import com.domain.repositories.items.ItemsRepository
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
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
import org.mockito.MockitoAnnotations
import java.util.regex.Pattern

class DashboardViewModelTest {
    private lateinit var dashboardViewModel: DashboardViewModel
    @Mock
    private lateinit var mockApplication: Application
    @Mock
    private lateinit var authenticationRepository: AuthenticationRepository
    @Mock
    private lateinit var itemsRepository: ItemsRepository

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        dashboardViewModel = DashboardViewModel(mockApplication, authenticationRepository, itemsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test refresh list fail`() = runBlockingTest {
        val url = "https://domain.tld"
        val position = 0
        val expected = null

        Mockito.`when`(dashboardViewModel.itemsRepository.refreshList(url)).thenReturn(null)
        dashboardViewModel.updateList(url,  position)
        val actual = ""

        assertEquals(actual, expected)
    }

    @Test
    fun `test refresh list success`() = runBlockingTest {
        val url = "https://domain.tld"
        val position = 0
        val expected = ItemCategory("1", "title", null, null, null)

        Mockito.`when`(dashboardViewModel.itemsRepository.refreshList(url)).thenReturn(null)
        dashboardViewModel.updateList(url,  position)
        val actual = ""

        assertEquals(actual, expected)
    }

    @Test
    fun `test card info fail`() = runBlockingTest {
        val url = "https://domain.tld"
        val expected = null

        val categoryPosition = 0
        val itemPosition = 0

        Mockito.`when`(dashboardViewModel.itemsRepository.getCardInfo(url)).thenReturn(null)
        dashboardViewModel.getCardInfo(url,  categoryPosition, itemPosition)
        val actual = ""

        assertEquals(actual, expected)
    }

    @Test
    fun `test card info success`() = runBlockingTest {
        val url = "https://domain.tld"
        val expected = Item("1", "item Name", null, null, true)

        val categoryPosition = 0
        val itemPosition = 0

        Mockito.`when`(dashboardViewModel.itemsRepository.getCardInfo(url)).thenReturn(null)
        dashboardViewModel.getCardInfo(url,  categoryPosition, itemPosition)
        val actual = ""

        assert(false)
    }

}