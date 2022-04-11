package com.domain.myapplication.base.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.domain.myapplication.drawerController.MyDrawerController

abstract class BaseFragment : Fragment() {
   protected lateinit var drawerController: MyDrawerController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        drawerController.showBottomNav()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        drawerController = context as MyDrawerController
        drawerController.showBottomNav()
    }

    override fun onResume() {
        super.onResume()
        drawerController.currentFragment = this
    }

    open fun onBackPressed() {}

    open fun onKeyDown(keyCode: Int, event: KeyEvent?){
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> { /*onBackPressed()*/ }
        }
    }

}