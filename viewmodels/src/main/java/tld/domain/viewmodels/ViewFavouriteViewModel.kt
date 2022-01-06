package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.models.FavItem

class ViewFavouriteViewModel(application: Application) : AndroidViewModel(application){
    private val _favItem: MutableLiveData<FavItem> = MutableLiveData()
    val favItem: MutableLiveData<FavItem>
        get() = _favItem

    fun setFunItem(favItem: FavItem){
        _favItem.value = favItem
    }

}