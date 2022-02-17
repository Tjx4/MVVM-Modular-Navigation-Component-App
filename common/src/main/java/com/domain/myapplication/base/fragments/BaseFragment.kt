package com.domain.myapplication.base.fragments

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.domain.myapplication.drawerController.MyDrawerController
import com.domain.myapplication.extensions.vibratePhone

abstract class BaseFragment : Fragment() {
   protected lateinit var drawerController: MyDrawerController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        drawerController = context as MyDrawerController
        drawerController.showBottomNav()

        requireActivity().onBackPressedDispatcher.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )

        //requireActivity().on
    }

    open fun onBackPressed(){
        drawerController.popBack()
    }
}