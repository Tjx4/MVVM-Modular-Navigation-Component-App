package com.domain.myapplication.extensions

import android.app.Activity
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.domain.myapplication.R
import com.domain.myapplication.base.fragments.BaseFragment
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.domain.myapplication.drawerController.MyDrawerController
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupWithCustomAnimNavController(myDrawerController: MyDrawerController, navController: NavController, enterAnim: Int, exitAnim: Int, popEnterAnim: Int, popExitAnim: Int) {

    var lasttItmeIdex = 0

    this.setOnNavigationItemSelectedListener { item ->

        val options1 = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(enterAnim)
            .setExitAnim(exitAnim)
            .setPopEnterAnim(popEnterAnim)
            .setPopExitAnim(popExitAnim)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

        val options2 = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.no_transition)
            .setExitAnim(exitAnim)
            .setPopEnterAnim(R.anim.no_transition)
            .setPopExitAnim(popExitAnim)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

       val itemIndex = when (item.itemId) {
            R.id.dashboardFragment -> 0
            R.id.videosFragment -> 1
            R.id.downloadsFragment -> 2
           else -> 0
        }

        val options = if(itemIndex >= lasttItmeIdex)  options1 else options2

        when (item.itemId) {
            R.id.dashboardFragment -> navController.navigate(R.id.dashboardFragment, null, options)
            R.id.videosFragment -> navController.navigate(R.id.videosFragment, null, options)
            R.id.downloadsFragment -> navController.navigate(R.id.downloadsFragment, null, options)
        }

        lasttItmeIdex = itemIndex

        true
    }

    this.setOnNavigationItemReselectedListener { item ->
        when(myDrawerController.currentFragment is TopNavigationFragment ){
            true -> {}
            else -> myDrawerController.onBackNav()
        }

        return@setOnNavigationItemReselectedListener
    }
}
