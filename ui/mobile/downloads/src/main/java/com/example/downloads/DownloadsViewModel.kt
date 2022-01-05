package com.example.downloads

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DownloadsViewModel(private val app: Application) : AndroidViewModel(app){

    private val _count: MutableLiveData<String> = MutableLiveData()
    val count: MutableLiveData<String>
        get() = _count

    private val _isComplete: MutableLiveData<Boolean> = MutableLiveData()
    val isComplete: MutableLiveData<Boolean>
        get() = _isComplete

    init {
        val flow = flow<Int> {
            for (i in 5 downTo 0){
                emit(i)
                delay(2000)
            }
        }

        viewModelScope.launch (Dispatchers.Main) {
            flow.collect {
                when(it){
                    0 -> _isComplete.value = true
                    else -> _count.value = "$it"
                }
            }
        }
    }

}