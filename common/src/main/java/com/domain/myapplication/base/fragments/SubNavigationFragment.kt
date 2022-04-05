package com.domain.myapplication.base.fragments

abstract class SubNavigationFragment : BaseFragment()  {
    override fun onBackPressed(){
        drawerController.popBack()
    }
}