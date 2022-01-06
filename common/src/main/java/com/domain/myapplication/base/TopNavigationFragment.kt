package com.domain.myapplication.base

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.domain.myapplication.R

abstract class TopNavigationFragment : BaseFragment()  {

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (enter) {
            AnimationUtils.loadAnimation(context, R.anim.trail_out)
        } else {
            AnimationUtils.loadAnimation(context, R.anim.trail_in)
        }
    }

}