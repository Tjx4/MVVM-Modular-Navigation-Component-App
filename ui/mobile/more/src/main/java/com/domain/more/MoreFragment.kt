package com.domain.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.domain.more.databinding.FragmentMoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.viewmodels.MoreViewModel

class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding
    private val moreViewModel: MoreViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)
        binding.lifecycleOwner = this
        binding.moreViewModel = moreViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}