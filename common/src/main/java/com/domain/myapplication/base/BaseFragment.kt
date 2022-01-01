package com.domain.myapplication.base

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.domain.myapplication.drawerController.MyDrawerController

abstract class BaseFragment : Fragment() {
   protected lateinit var drawerController: MyDrawerController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        drawerController = context as MyDrawerController
        requireActivity().onBackPressedDispatcher.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )
    }

    open fun onBackPressed(){}
}