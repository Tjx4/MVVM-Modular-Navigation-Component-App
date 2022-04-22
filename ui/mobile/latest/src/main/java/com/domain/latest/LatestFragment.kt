package com.domain.latest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.domain.latest.databinding.FragmentLatestBinding
import com.domain.myapplication.base.fragments.TopNavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.viewmodels.LatestViewModel

class LatestFragment : TopNavigationFragment() {
    private lateinit var binding: FragmentLatestBinding
    private val latestViewModel: LatestViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        drawerController.showBottomNav()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_latest, container, false)
        binding.lifecycleOwner = this
        binding.latestViewModel = latestViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}