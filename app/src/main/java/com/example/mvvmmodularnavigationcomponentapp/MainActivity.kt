package com.example.mvvmmodularnavigationcomponentapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        bnBottomNav.setupWithCustomAnimNavController(this, navController,  com.example.common.R.anim.trail_out, com.example.common.R.anim.trail_in, com.example.common.R.anim.trail_out, com.example.common.R.anim.trail_in)
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
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToViewItemFragment(item)
        navController.navigate(action)
    }

    override fun navigateFromVideoToViewItem(item: Item) {
        val action = VideosFragmentDirections.actionVideosFragmentToViewItemFragment(item)
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

    override fun showBottomNav(onShow: () -> Unit) {
        bnBottomNav?.let {
            it.visibility = View.VISIBLE

            it.animate()
                .translationY(0f)
                //.alpha(1f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        onShow.invoke()
                    }
                })
        }

    }

    override fun hideBottomNav(onHide: () -> Unit) {
        bnBottomNav?.let {
            it.animate()
                .translationY(it.height.toFloat())
                //.alpha(0.0f)
                .setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        it.visibility = View.GONE
                        onHide.invoke()
                    }
                })
        }
    }

    override fun onBackNav() {
        onBackPressed()
    }

    fun handleTopNavigation() {
        when(bnBottomNav.selectedItemId) {
            NavigationScreens.Dashboard.fragmentId -> finish()
            else -> bnBottomNav.selectedItemId = NavigationScreens.Dashboard.fragmentId
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