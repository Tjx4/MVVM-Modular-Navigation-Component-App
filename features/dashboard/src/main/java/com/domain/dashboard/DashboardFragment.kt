package com.domain.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.domain.dashboard.databinding.FragmentDashboardBinding
import com.domain.myapplication.adapters.PopularPairsPagingAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var popularPairsPagingAdapter: PopularPairsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.lifecycleOwner = this
        binding.dashboardViewModel = dashboardViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun addObservers() {
        //dashboardViewModel.canProceed.observe(viewLifecycleOwner, { btnRequestHistory.visibility = View.VISIBLE })
        //dashboardViewModel.hideProceed.observe(viewLifecycleOwner, { btnRequestHistory.visibility = View.INVISIBLE })
        //dashboardViewModel.selectedPairMessage.observe(viewLifecycleOwner, { onPairSelected(it)})
    }
}