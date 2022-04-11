package tld.domain.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.constants.PAYLOAD_KEY
import com.domain.myapplication.constants.VIDEO_ID
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.helpers.showErrorDialog
import com.domain.myapplication.helpers.vibratePhone
import com.domain.myapplication.models.Video
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.player.databinding.ActivityPlayerBinding
import tld.domain.viewmodels.PlayerViewModel


abstract class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private val playerViewModel: PlayerViewModel by viewModel()

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    //private var playbackPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding.playerViewModel = playerViewModel
        binding.lifecycleOwner = this

        val videoId = intent.extras?.getBundle(PAYLOAD_KEY)?.getString(VIDEO_ID)
        playerViewModel.setVideoId(videoId)

        imgBtnBack.setOnClickListener {
            vibratePhone(this, DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                onBackPressed()
            })

        }

        addObservers()
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            playerViewModel.video.value?.high?.let {
                initializePlayer(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            playerViewModel.video.value?.high?.let {
                initializePlayer(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun addObservers() {
        playerViewModel.isLoading.observe(this, { onLoading() })
        playerViewModel.showVideo.observe(this, { showContent() })
        playerViewModel.videoIdError.observe(this, { onVideoIdError() })
        playerViewModel.videoId.observe(this, { onVideoIdSet(it) })
        playerViewModel.videoError.observe(this, { onVideoError(it) })
        playerViewModel.video.observe(this, { onVideoSet(it) })
        playerViewModel.noVideo.observe(this, { onNoVideoFound() })
        playerViewModel.videoUri.observe(this, { initializePlayer(it) })
    }

    private fun onLoading(){
        avlPlayerLoader.visibility = View.VISIBLE
    }

    private fun showContent(){
        avlPlayerLoader.visibility = View.GONE
    }

    private fun onVideoIdError(){
        showContent()

        showErrorDialog(
            this,
            getString(R.string.error),
            getString(tld.domain.viewmodels.R.string.no_video_id),
            getString(R.string.close)
        ) {
            finish()
        }
    }

    private fun onVideoIdSet(videoId: String){
        fetchVideoById(videoId)
    }

    private fun onVideoError(errorMessage: String){
        showContent()

        showErrorDialog(
            this,
            getString(R.string.error),
            errorMessage,
            getString(R.string.retry)
        ) {
            playerViewModel.videoId.value?.let {
                avlPlayerLoader.visibility = View.VISIBLE
                fetchVideoById(it)
            }
        }
    }

    private fun fetchVideoById(videoId: String){
        playerViewModel.let {
            it.viewModelScope.launch(Dispatchers.IO) {
                it.getVideo(videoId)
            }
        }
    }

    private fun onVideoSet(video: Video){
        playerViewModel.startPlayback(video)
    }

    private fun onNoVideoFound(){
        showContent()

        showErrorDialog(
            this,
            getString(R.string.error),
            getString(tld.domain.viewmodels.R.string.no_video),
            getString(R.string.close)
        ) {
            finish()
        }
    }

    private fun initializePlayer(url: String) {
        if(player == null){
            val trackSelector = DefaultTrackSelector(this)
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())

            val loadControl = DefaultLoadControl.Builder()
                .setBufferDurationsMs(32 * 1024, 64 * 1024, 1024, 1024)
                .createDefaultLoadControl()

            player = SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()

            video_view.player = player
        }

        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)

        val secondMediaItem = MediaItem.Builder()
            .setUri(getString(R.string.media_url_dash))
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()
        player?.addMediaItem(secondMediaItem)

/*
//Dash?

        player = SimpleExoPlayer.Builder(this).build()
        video_view.player = player

        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)

// val secondMediaItem = MediaItem.fromUri("http://appicsoftware.xyz/api/cars/videos/bugatti/4k_bug.mp4")
// player?.addMediaItem(secondMediaItem)
*/

        player?.let {
            it.playWhenReady = playWhenReady
            it.seekTo(currentWindow, it.currentPosition)
            it.prepare()
        }

        addPlayerListener()
    }

    private fun addPlayerListener() {
        player?.addListener(object : Player.EventListener {
            fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                playerViewModel.onPlayerStateChanged(playWhenReady, playbackState)
            }

            override fun onPlayerError(error: ExoPlaybackException) {
                //playbackPosition = player?.currentPosition ?: 0
                playerViewModel.handlePlayerError(error)
            }

            fun onPositionDiscontinuity() {

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

            }
        })

        video_view.setControllerVisibilityListener { visibility ->
            //Todo move logic
            if (visibility == View.VISIBLE) {
                imgBtnBack.visibility = View.VISIBLE
            } else {
                imgBtnBack.visibility = View.GONE
            }
        }
    }

    private fun releasePlayer() {
        player?.let {
            //playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        video_view.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }
}