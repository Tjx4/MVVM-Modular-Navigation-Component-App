package com.example.mvvmmodularnavigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.domain.dashboard.DashboardFragmentDirections
import com.domain.myapplication.base.fragments.BaseFragment
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.domain.myapplication.constants.PLAYER_ACTIVITY
import com.domain.myapplication.constants.VIDEO_ID
import com.domain.myapplication.drawerController.MyDrawerController
import com.domain.myapplication.enums.NavigationScreens
import com.domain.myapplication.extensions.FADE_IN_ACTIVITY
import com.domain.myapplication.extensions.navigateToActivity
import com.domain.myapplication.extensions.setupWithCustomAnimNavController
import com.domain.myapplication.models.Item
import kotlinx.android.synthetic.main.activity_main.*
import tld.domain.favourites.FavouritesFragmentDirections
import tld.domain.login.LoginFragmentDirections
import tld.domain.videos.VideosFragmentDirections

class MainActivity : AppCompatActivity(), MyDrawerController {
    override var currentFragment: BaseFragment? = null
    override lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.navControllerFragment)
        bnBottomNav.setupWithCustomAnimNavController(this, navController, R.anim.trail_out, R.anim.trail_in, R.anim.trail_out, R.anim.trail_in)
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

    override fun navigateFromDashboardToViewItem(item: Item) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToViewItemFragment(item)
        navController.navigate(action)
    }

    override fun navigateFromViewItemToPlayer(videoId: String) {
        val payload = Bundle()
        payload.putString(VIDEO_ID, videoId)
        navigateToActivity(PLAYER_ACTIVITY, payload, FADE_IN_ACTIVITY)
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
        bnBottomNav?.visibility = View.INVISIBLE
    }

    override fun onBackNav() {
        onBackPressed()
    }

    fun handleTopNavigation() {
        when(bnBottomNav.selectedItemId) {
            NavigationScreens.dashboard.fragmentId -> finish()
            else -> bnBottomNav.selectedItemId = NavigationScreens.dashboard.fragmentId
        }
    }

    override fun onBackPressed() {
        when(currentFragment is TopNavigationFragment){
            true -> handleTopNavigation()
            false -> currentFragment?.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(currentFragment is TopNavigationFragment){
            true -> handleTopNavigation()
            else -> currentFragment?.onBackPressed()
        }

        return false
    }
}