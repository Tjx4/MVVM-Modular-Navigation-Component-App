package tld.domain.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.domain.myapplication.constants.MAIN_ACTIVITY
import com.domain.myapplication.extensions.FADE_IN_ACTIVITY
import com.domain.myapplication.extensions.navigateToActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToActivity(MAIN_ACTIVITY, null, FADE_IN_ACTIVITY)
        finish()
    }
}