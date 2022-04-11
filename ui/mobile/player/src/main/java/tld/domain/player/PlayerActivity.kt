package tld.domain.player

import android.os.Bundle
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.constants.FAV_ITEM
import com.domain.myapplication.constants.PAYLOAD_KEY
import com.domain.myapplication.constants.VIDEO_ID
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.helpers.vibratePhone
import com.domain.myapplication.models.Item
import com.example.common.player.BasePlayerActivity
import kotlinx.android.synthetic.main.activity_player.*
import tld.domain.player.databinding.ActivityPlayerBinding

class PlayerActivity : BasePlayerActivity() {
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding.playerViewModel = playerViewModel
        binding.lifecycleOwner = this

        val videoId = intent.extras?.getString(VIDEO_ID)
        playerViewModel.setVideoId(videoId)

        initPlayerViews()
        addObservers()
    }

    override fun initPlayerViews(){
        playerView = video_view
        avLoadingIndicatorView = avlPlayerLoader

        btnBack = imgBtnBack
        btnBack?.setOnClickListener {
            vibratePhone(this, DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                onBackPressed()
            })
        }
    }

}