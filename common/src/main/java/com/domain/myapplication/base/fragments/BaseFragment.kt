package com.domain.myapplication.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.domain.myapplication.drawerController.MyDrawerController

abstract class BaseFragment : Fragment() {
   protected lateinit var drawerController: MyDrawerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        drawerController = context as MyDrawerController
        drawerController.showBottomNav()

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

    override fun onResume() {
        super.onResume()
        drawerController.currentFragment = this
    }

    open fun onBackPressed(){
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?){
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {}//onBackPressed()
        }
    }

}