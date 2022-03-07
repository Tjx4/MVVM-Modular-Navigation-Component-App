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
    val options = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(enterAnim)
        .setExitAnim(exitAnim)
        .setPopEnterAnim(popEnterAnim)
        .setPopExitAnim(popExitAnim)
        .setPopUpTo(navController.graph.startDestination, false)
        .build()

    this.setOnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.dashboardFragment -> navController.navigate(R.id.dashboardFragment, null, options)
            R.id.videosFragment -> navController.navigate(R.id.videosFragment, null, options)
            R.id.downloadsFragment -> navController.navigate(R.id.downloadsFragment, null, options)
        }

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
