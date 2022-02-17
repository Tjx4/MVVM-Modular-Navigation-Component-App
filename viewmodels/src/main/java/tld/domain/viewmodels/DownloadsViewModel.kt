package tld.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.base.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadsViewModel(application: Application) : BaseViewModel(application){

    private val _count: MutableLiveData<String> = MutableLiveData()
    val count: MutableLiveData<String>
        get() = _count

    private val _isComplete: MutableLiveData<Boolean> = MutableLiveData()
    val isComplete: MutableLiveData<Boolean>
        get() = _isComplete

    init {
        viewModelScope.launch (Dispatchers.IO) {
            val flow = flow<Int> {
                for (i in 5 downTo 0){
                    emit(i)
                    delay(1000)
                }
            }

            withContext(Dispatchers.Main){
                flow.collect {
                    when(it){
                        0 -> _isComplete.value = true
                        else -> _count.value = "$it"
                    }
                }
            }
        }
    }

}