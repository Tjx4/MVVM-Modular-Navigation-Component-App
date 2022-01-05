package com.domain.myapplication.drawerController

interface MyDrawerController {
    fun navigateFromLoginToDashboard()
    fun navigateFromDashboardToFavourites(message: String)
    fun popBack()
    fun showBottomNav()
    fun hideBottomNav()
}