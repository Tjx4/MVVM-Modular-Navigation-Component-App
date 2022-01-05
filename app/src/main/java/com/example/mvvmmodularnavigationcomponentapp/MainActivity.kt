package com.example.mvvmmodularnavigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.domain.dashboard.DashboardFragmentDirections
import com.domain.myapplication.drawerController.MyDrawerController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import tld.domain.login.LoginFragmentDirections

class MainActivity : AppCompatActivity(), MyDrawerController {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FirebaseApp.initializeApp(this)
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navControllerFragment)
        bnBottomNav.setupWithNavController(navController)
    }

    override fun navigateFromLoginToDashboard() {
        val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
        navController.navigate(action)
    }

    override fun navigateFromDashboardToFavourites(message: String) {
         val action = DashboardFragmentDirections.dashboardFragmentToFavouritesFragment(message)
        navController.navigate(action)
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