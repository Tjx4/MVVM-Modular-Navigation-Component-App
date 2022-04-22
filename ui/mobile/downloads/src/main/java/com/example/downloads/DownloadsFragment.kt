package com.example.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.example.downloads.databinding.FragmentDownloadsBinding
import kotlinx.android.synthetic.main.fragment_downloads.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.viewmodels.DownloadsViewModel

class DownloadsFragment : TopNavigationFragment() {
    private lateinit var binding: FragmentDownloadsBinding
    private val downloadsViewModel: DownloadsViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        drawerController.showBottomNav()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false)
        binding.lifecycleOwner = this
        binding.downloadsViewModel = downloadsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        downloadsViewModel.count.observe(viewLifecycleOwner, { onCountSet(it) })
        downloadsViewModel.isComplete.observe(viewLifecycleOwner, { onComplete() })
    }

    fun onCountSet(count: String){

    }

    fun onComplete(){
        tvText.visibility = View.GONE
        Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show()
    }
}