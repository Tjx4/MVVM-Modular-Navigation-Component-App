package tld.domain.player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.constants.PAYLOAD_KEY
import com.domain.myapplication.constants.VIDEO_ID
import com.domain.myapplication.helpers.showErrorDialog
import com.domain.myapplication.models.Video
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.player.databinding.ActivityPlayerBinding
import tld.domain.viewmodels.PlayerViewModel

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private val playerViewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding.playerViewModel = playerViewModel
        binding.lifecycleOwner = this

        val videoId = intent.extras?.getBundle(PAYLOAD_KEY)?.getString(VIDEO_ID)
        playerViewModel.setVideoId(videoId)

        addObservers()
    }

    private fun addObservers() {
        playerViewModel.isLoading.observe(this, { onLoading() })
        playerViewModel.videoIdError.observe(this, { onVideoIdError() })
        playerViewModel.videoId.observe(this, { onVideoIdSet(it) })
        playerViewModel.videoError.observe(this, { onVideoError(it) })
        playerViewModel.video.observe(this, { onVideoSet(it) })
    }

    private fun onLoading(){
        avlPlayerLoader.visibility = View.VISIBLE
    }

    private fun onVideoIdError(){
        avlPlayerLoader.visibility = View.GONE

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
        avlPlayerLoader.visibility = View.GONE
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
        avlPlayerLoader.visibility = View.GONE
        playerViewModel.startPlayback(video)
    }

}