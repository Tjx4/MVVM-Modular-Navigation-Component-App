package com.domain.myapplication.drawerController

import androidx.navigation.NavController
import com.domain.myapplication.models.Item

interface MyDrawerController {
    var navController: NavController
    fun navigateFromLoginToDashboard()
    fun navigateFromDashboardToFavourites()
    fun navigateFromFavouritesToViewItem(item: Item)
    fun navigateFromVideoToViewItem(item: Item)
    fun popAll()
    fun popBack()
    fun showBottomNav()
    fun hideBottomNav()
}