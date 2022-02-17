package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.domain.myapplication.models.Item

class ViewItemViewModel(application: Application) : AndroidViewModel(application){
    private val _Item: MutableLiveData<Item> = MutableLiveData()
    val item: MutableLiveData<Item>
        get() = _Item

    fun setFunItem(item: Item){
        _Item.value = item
    }

}