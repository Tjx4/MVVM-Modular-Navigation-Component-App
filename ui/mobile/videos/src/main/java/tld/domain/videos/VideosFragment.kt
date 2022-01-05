package tld.domain.videos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.videos.databinding.FragmentVideosBinding

class VideosFragment : BaseFragment() {
    private lateinit var binding: FragmentVideosBinding
    private val videosViewModel: VideosViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false)
        binding.lifecycleOwner = this
        binding.videosViewModel = videosViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}