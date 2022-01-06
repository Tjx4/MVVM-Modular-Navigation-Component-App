package com.domain.myapplication.drawerController

import androidx.navigation.NavController

interface MyDrawerController {
    var navController: NavController
    fun navigateFromLoginToDashboard()
    fun navigateFromDashboardToFavourites(message: String)
    fun popAll()
    fun popBack()
    fun showBottomNav()
    fun hideBottomNav()
}