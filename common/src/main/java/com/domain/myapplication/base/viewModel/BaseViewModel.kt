package com.domain.myapplication.base.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application){
    protected val app = application

}