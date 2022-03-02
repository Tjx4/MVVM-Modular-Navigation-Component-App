package tld.domain.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.fragments.TopNavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.player.databinding.FragmentPlayerBinding
import tld.domain.viewmodels.PlayerViewModel
import android.content.pm.ActivityInfo
import androidx.lifecycle.viewModelScope
import com.domain.myapplication.constants.VIDEO_ID
import com.domain.myapplication.helpers.showErrorDialog
import com.domain.myapplication.models.Video
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerFragment : TopNavigationFragment(){
    private lateinit var binding: FragmentPlayerBinding
    private val playerViewModel: PlayerViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false)
        binding.lifecycleOwner = this
        binding.playerViewModel = playerViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()

        val videoId = arguments?.getString(VIDEO_ID)
        playerViewModel.setVideoId(videoId)
    }

    private fun addObservers() {
        playerViewModel.isLoading.observe(viewLifecycleOwner, { onLoading() })
        playerViewModel.videoIdError.observe(viewLifecycleOwner, { onVideoIdError() })
        playerViewModel.videoId.observe(viewLifecycleOwner, { onVideoIdSet(it) })
        playerViewModel.videoError.observe(viewLifecycleOwner, { onVideoError(it) })
        playerViewModel.video.observe(viewLifecycleOwner, { onVideoSet(it) })
    }

    private fun onLoading(){
        avlPlayerLoader.visibility = View.VISIBLE
    }

    private fun onVideoIdError(){
        avlPlayerLoader.visibility = View.GONE

        showErrorDialog(
            requireContext(),
            getString(R.string.error),
            getString(tld.domain.viewmodels.R.string.no_video_id),
            getString(R.string.close)
        ) {
            drawerController.popBack()
        }
    }

    private fun onVideoIdSet(videoId: String){
        fetchVideoById(videoId)
    }

    private fun onVideoError(errorMessage: String){
        avlPlayerLoader.visibility = View.GONE
        showErrorDialog(
            requireContext(),
            getString(R.string.error),
            errorMessage,
            getString(R.string.retry)
        ) {
            playerViewModel.videoId.value?.let {
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

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

}