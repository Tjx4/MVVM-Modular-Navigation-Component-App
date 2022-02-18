package com.domain.myapplication.base.fragments

import android.content.Context
import android.view.KeyEvent
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
        drawerController.currentFragment = this

        /*
        requireActivity().onBackPressedDispatcher.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )
        */
    }

    open fun onBackPressed(){
        drawerController.popBack()
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?){
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {}//onBackPressed()
        }
    }

}