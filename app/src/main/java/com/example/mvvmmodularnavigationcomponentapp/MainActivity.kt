package com.example.mvvmmodularnavigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.domain.dashboard.DashboardFragmentDirections
import com.domain.myapplication.drawerController.MyDrawerController
import com.domain.myapplication.extensions.setupWithCustomAnimNavController
import com.domain.myapplication.models.Item
import kotlinx.android.synthetic.main.activity_main.*
import tld.domain.favourites.FavouritesFragmentDirections
import tld.domain.login.LoginFragmentDirections
import tld.domain.videos.VideosFragmentDirections

class MainActivity : AppCompatActivity(), MyDrawerController {
    override lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.navControllerFragment)
        bnBottomNav.setupWithCustomAnimNavController(navController, R.anim.trail_out, R.anim.trail_in, R.anim.trail_out, R.anim.trail_in)
    }

    override fun navigateFromLoginToDashboard() {
        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        navController.navigate(action)
    }

    override fun navigateFromDashboardToFavourites() {
         val action = DashboardFragmentDirections.actionDashboardFragmentToFavouritesFragment()
        navController.navigate(action)
    }

    override fun navigateFromFavouritesToViewItem(item: Item) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToViewFavouriteFragment(item)
        navController.navigate(action)
    }

    override fun navigateFromVideoToViewItem(item: Item) {
        val action = VideosFragmentDirections.actionVideosFragmentToViewFavouriteFragment(item)
        navController.navigate(action)
    }

    override fun popAll() {
        navController.popBackStack()
        navController.navigate(R.id.loginFragment)
    }

    override fun popBack() {
        navController.popBackStack()
    }

    override fun showBottomNav() {
        bnBottomNav?.visibility = View.VISIBLE
    }

    override fun hideBottomNav() {
        bnBottomNav?.visibility = View.GONE
    }

    override fun onBackPressed() {
        //Todo fix back nav
        if (bnBottomNav.selectedItemId == R.id.dashboardFragment) {
            super.onBackPressed()
        }  else {
            bnBottomNav.selectedItemId = R.id.dashboardFragment
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return false
        }

        return super.onKeyDown(keyCode, event)
    }
}