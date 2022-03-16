package com.domain.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.domain.dashboard.databinding.FragmentDashboardBinding
import com.domain.myapplication.base.fragments.TopNavigationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tld.domain.viewmodels.DashboardViewModel

class DashboardFragment : TopNavigationFragment() {
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
            dashboardViewModel.viewModelScope.launch (Dispatchers.IO){
                dashboardViewModel.logOutUser()
            }
        }

        btnNext.setOnClickListener {
            drawerController.navigateFromDashboardToFavourites()
        }
    }

    override fun onBackPressed() {
        activity?.moveTaskToBack(true)
    }

    private fun addObservers() {
        dashboardViewModel.showLoading.observe(viewLifecycleOwner, { showLoading() })
        dashboardViewModel.logout.observe(viewLifecycleOwner, { onLogOut() })
    }

    fun showLoading(){

    }

    fun onLogOut(){
        drawerController.popAll()
    }

}