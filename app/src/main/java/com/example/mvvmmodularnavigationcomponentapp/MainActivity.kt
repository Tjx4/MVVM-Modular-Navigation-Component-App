package com.example.mvvmmodularnavigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.domain.dashboard.DashboardFragmentDirections
import com.domain.myapplication.drawerController.MyDrawerController
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity(), MyDrawerController {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FirebaseApp.initializeApp(this);
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        setContentView(R.layout.activity_main)
    }

    override fun navigateFromDashboardToFavourites(message: String) {
         val action = DashboardFragmentDirections.dashboardFragmentToFavouritesFragment(message)
         this.findNavController(R.id.navControllerFragment).navigate(action)
    }

    override fun popBack() {
        this.findNavController(R.id.navControllerFragment).popBackStack()
    }
}