package com.domain.myapplication.base.fragments

import android.view.KeyEvent

abstract class TopNavigationFragment : BaseFragment()  {
    /*
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, R.anim.trail_out)
        } else {
            AnimationUtils.loadAnimation(context, R.anim.trail_in)
        }
    }
    */


    override fun onBackPressed(){
    }

}