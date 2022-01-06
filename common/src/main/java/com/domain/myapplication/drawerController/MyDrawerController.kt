package com.domain.myapplication.drawerController

import androidx.navigation.NavController
import com.domain.myapplication.models.FavItem

interface MyDrawerController {
    var navController: NavController
    fun navigateFromLoginToDashboard()
    fun navigateFromDashboardToFavourites()
    fun navigateFromFavouritesToViewFavourites(favItem: FavItem)
    fun popAll()
    fun popBack()
    fun showBottomNav()
    fun hideBottomNav()
}