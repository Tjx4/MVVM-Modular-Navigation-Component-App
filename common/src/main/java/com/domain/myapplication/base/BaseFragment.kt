package com.domain.myapplication.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.domain.myapplication.drawerController.MyDrawerController

abstract class BaseFragment : Fragment() {
   protected lateinit var myDrawerController: MyDrawerController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myDrawerController = context as MyDrawerController
    }

}