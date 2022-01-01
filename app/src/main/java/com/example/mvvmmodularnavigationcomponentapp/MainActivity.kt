package com.example.mvvmmodularnavigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.domain.dashboard.DashboardFragmentDirections
import com.domain.myapplication.drawerController.MyDrawerController

class MainActivity : AppCompatActivity(), MyDrawerController {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FirebaseApp.initializeApp(this)
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        setContentView(R.layout.activity_main)

        navController = this.findNavController(R.id.navControllerFragment)
        //setupWithNavController(navController)
    }

    override fun navigateFromDashboardToFavourites(message: String) {
         val action = DashboardFragmentDirections.dashboardFragmentToFavouritesFragment(message)
        navController.navigate(action)
    }

    override fun popBack() {
        navController.popBackStack()
    }
}