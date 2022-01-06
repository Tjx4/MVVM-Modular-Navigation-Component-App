package com.example.mvvmmodularnavigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.domain.dashboard.DashboardFragmentDirections
import com.domain.myapplication.drawerController.MyDrawerController
import com.domain.myapplication.extensions.setupWithNavControllerWithCustomAnim
import kotlinx.android.synthetic.main.activity_main.*
import tld.domain.login.LoginFragmentDirections

class MainActivity : AppCompatActivity(), MyDrawerController {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.navControllerFragment)

        //bnBottomNav.setupWithNavController(navController)
        bnBottomNav.setupWithNavControllerWithCustomAnim(navController, R.anim.trail_out, R.anim.trail_in, R.anim.trail_out, R.anim.trail_in)
    }

    override fun navigateFromLoginToDashboard() {
        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        navController.navigate(action)
    }

    override fun navigateFromDashboardToFavourites(message: String) {
         val action = DashboardFragmentDirections.dashboardFragmentToFavouritesFragment(message)
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
}