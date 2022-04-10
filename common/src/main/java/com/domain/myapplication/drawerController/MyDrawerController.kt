package com.domain.myapplication.drawerController

import androidx.navigation.NavController
import com.domain.myapplication.base.fragments.BaseFragment
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.Video

interface MyDrawerController {
    var currentFragment: BaseFragment?
    var navController: NavController
    fun onBackNav()
    fun navigateFromLoginToDashboard()
    fun navigateFromDashboardToFavourites()
    fun navigateFromFavouritesToViewItem(item: Item)
    fun navigateFromVideoToViewItem(item: Item)
    fun navigateFromDashboardToViewItem(item: Item)
    fun navigateFromViewItemToPlayer(videoId: String)
    fun popAll()
    fun popBack()
    fun showBottomNav(onShow: () -> Unit = {})
    fun hideBottomNav(onHide: () -> Unit = {})
}