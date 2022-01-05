package com.example.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.BaseFragment
import com.example.downloads.databinding.FragmentDownloadsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadsFragment : BaseFragment() {
    private lateinit var binding: FragmentDownloadsBinding
    private val downloadsViewModel: DownloadsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_downloads, container, false)
        binding.lifecycleOwner = this
        binding.downloadsViewModel = downloadsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}