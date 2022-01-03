package com.domain.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.domain.dashboard.databinding.FragmentDashboardBinding
import com.domain.myapplication.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        binding.lifecycleOwner = this
        binding.dashboardViewModel = dashboardViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()

        btnLogout.setOnClickListener {
            drawerController.popBack()
        }

        btnNext.setOnClickListener {
            val message = "Hello"
            drawerController.navigateFromDashboardToFavourites(message)
        }
    }

    override fun onBackPressed() {
        activity?.moveTaskToBack(true)
    }

    private fun addObservers() {
        dashboardViewModel.showLoading.observe(viewLifecycleOwner, { showLoading() })
    }

    fun showLoading(){

    }

}