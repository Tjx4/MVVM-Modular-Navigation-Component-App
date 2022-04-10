package com.domain.myapplication.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.domain.myapplication.R
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.domain.myapplication.drawerController.MyDrawerController
import com.domain.myapplication.enums.NavigationScreens
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setupWithCustomAnimNavController(myDrawerController: MyDrawerController, navController: NavController) {
    var lastItemIndex = 0

    this.setOnNavigationItemSelectedListener { item ->
        val enterOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.no_transition)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

        val exitOption = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.no_transition)
            .setPopExitAnim(R.anim.slide_out_left)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()

        val screen = NavigationScreens.values().first { it.fragmentId == item.itemId }
        val itemIndex = screen.index
        val navigationOptions = if(itemIndex >= lastItemIndex)  enterOptions else exitOption
        navController.navigate(screen.fragmentId, null, navigationOptions)
        lastItemIndex = itemIndex

        true
    }

    this.setOnNavigationItemReselectedListener { item ->
        when(myDrawerController.currentFragment is TopNavigationFragment ){
            true -> { /*No op*/ }
            else -> myDrawerController.onBackNav()
        }

        return@setOnNavigationItemReselectedListener
    }

}