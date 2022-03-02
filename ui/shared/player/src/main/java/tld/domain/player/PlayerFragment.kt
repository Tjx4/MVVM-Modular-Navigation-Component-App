package tld.domain.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.adapters.ItemsPagingAdapter
import com.domain.myapplication.base.fragments.TopNavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.player.databinding.FragmentPlayerBinding
import tld.domain.viewmodels.PlayerViewModel
import android.content.pm.ActivityInfo




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
    }

    private fun addObservers() {
       // playerViewModel.ld.observe(viewLifecycleOwner, { })
    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

}