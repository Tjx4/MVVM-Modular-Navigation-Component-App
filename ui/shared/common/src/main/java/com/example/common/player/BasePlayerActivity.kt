package com.example.common.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.helpers.showErrorDialog
import com.domain.myapplication.models.Video
import com.example.common.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.viewmodels.PlayerViewModel

abstract class BasePlayerActivity : AppCompatActivity() {
    protected val playerViewModel: PlayerViewModel by viewModel()
    protected var playerView: PlayerView? = null
    protected var btnBack: View? = null
    protected var avLoadingIndicatorView: AVLoadingIndicatorView? = null
    protected var player: SimpleExoPlayer? = null
    protected var playWhenReady = true
    protected var currentWindow = 0
    //protected var playbackPosition: Long = 0

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

    abstract fun initPlayerViews()

    protected fun addObservers() {
        playerViewModel.isLoading.observe(this) { onLoading() }
        playerViewModel.showVideo.observe(this) { showContent() }
        playerViewModel.showControls.observe(this) { showControls() }
        playerViewModel.hideControls.observe(this) { hideControls() }
        playerViewModel.videoIdError.observe(this) { onVideoIdError() }
        playerViewModel.videoId.observe(this) { onVideoIdSet(it) }
        playerViewModel.videoError.observe(this) { onVideoError(it) }
        playerViewModel.video.observe(this) { onVideoSet(it) }
        playerViewModel.noVideo.observe(this) { onNoVideoFound() }
        playerViewModel.videoUri.observe(this) { initializePlayer(it) }
    }

    protected fun onLoading() {
        avLoadingIndicatorView?.visibility = View.VISIBLE
    }

    protected fun showContent() {
        avLoadingIndicatorView?.visibility = View.GONE
    }

    protected fun showControls() {
        btnBack?.visibility = View.VISIBLE
    }

    protected fun hideControls() {
        btnBack?.visibility = View.GONE
    }

    protected fun onVideoIdError() {
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

    protected fun onVideoIdSet(videoId: String) {
        fetchVideoById(videoId)
    }

    protected fun onVideoError(errorMessage: String) {
        showContent()

        showErrorDialog(
            this,
            getString(R.string.error),
            errorMessage,
            getString(R.string.retry)
        ) {
            playerViewModel.videoId.value?.let {
                avLoadingIndicatorView?.visibility = View.VISIBLE
                fetchVideoById(it)
            }
        }
    }

    protected fun fetchVideoById(videoId: String) {
        playerViewModel.let {
            it.viewModelScope.launch(Dispatchers.IO) {
                it.getVideo(videoId)
            }
        }
    }

    protected fun onVideoSet(video: Video) {
        playerViewModel.startPlayback(video)
    }

    protected fun onNoVideoFound() {
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

    protected fun initializePlayer(url: String) {
        if (player == null) {
            val trackSelector = DefaultTrackSelector(this)
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())

            val loadControl = DefaultLoadControl.Builder()
                .setBufferDurationsMs(32 * 1024, 64 * 1024, 1024, 1024)
                .createDefaultLoadControl()

            player = SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()

            playerView?.player = player
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
*/

// val secondMediaItem = MediaItem.fromUri("http://appicsoftware.xyz/api/cars/videos/bugatti/4k_bug.mp4")
// player?.addMediaItem(secondMediaItem)


        player?.let {
            it.playWhenReady = playWhenReady
            it.seekTo(currentWindow, it.currentPosition)
            it.prepare()
        }

        addPlayerListener()
    }

    protected fun addPlayerListener() {
        player?.addListener(object : Player.EventListener {
            fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {

            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray,
                trackSelections: TrackSelectionArray
            ) {

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

        playerView?.setControllerVisibilityListener { visibility ->
            playerViewModel.toggleControllerVisible(visibility)
        }
    }

    protected fun releasePlayer() {
        player?.let {
            //playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }

    @SuppressLint("InlinedApi")
    protected fun hideSystemUi() {
        playerView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }
}