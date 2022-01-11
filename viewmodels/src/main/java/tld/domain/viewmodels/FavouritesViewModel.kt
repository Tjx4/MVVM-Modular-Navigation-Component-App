package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.models.FavItem
import com.domain.myapplication.models.Image
import com.domain.repositories.my.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(application: Application, private val myRepository: MyRepository) : AndroidViewModel(application) {
    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _favItems: MutableLiveData<List<FavItem>> = MutableLiveData()
    val favItems: MutableLiveData<List<FavItem>>
        get() = _favItems

    private val _noItems: MutableLiveData<Boolean> = MutableLiveData()
    val noItems: MutableLiveData<Boolean>
        get() = _noItems


    init {

        viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            getUserFavourites()
        }
    }

    suspend fun getUserFavourites() {
        val favourites = myRepository.getFavourites()

        withContext(Dispatchers.Main){
            when {
                favourites.isNullOrEmpty() -> _noItems.value = true
                else -> _favItems.value = favourites
            }
        }
    }

    fun addItemToFavourites(favItem: FavItem){
        viewModelScope.launch(Dispatchers.IO) {
            myRepository.saveFavouriteItem(favItem)
        }
    }

}