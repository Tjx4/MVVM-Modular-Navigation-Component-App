package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.models.FavItem

class FavouritesViewModel(application: Application) : AndroidViewModel(application){
    private val _favItems: MutableLiveData<List<FavItem>> = MutableLiveData()
    val favItems: MutableLiveData<List<FavItem>>
        get() = _favItems

    init {
        _favItems.value = arrayListOf<FavItem>(FavItem("Fav item 1", ""), FavItem("Fav item 2", ""))
    }

}