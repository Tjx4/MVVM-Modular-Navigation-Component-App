package com.domain.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.dashboard.databinding.FragmentDashboardBinding
import com.domain.myapplication.adapters.CategoriesPagingAdapter
import com.domain.myapplication.adapters.CategoryLoadStateAdapter
import com.domain.myapplication.adapters.ItemLoadStateAdapter
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.domain.myapplication.helpers.showErrorDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tld.domain.viewmodels.DashboardViewModel

class DashboardFragment : TopNavigationFragment(), CategoriesPagingAdapter.CategoryClickListener{
    private lateinit var binding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var categoriesPagingAdapter: CategoriesPagingAdapter

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
        initRecyclerView()

        btnLogout.setOnClickListener {
            dashboardViewModel.viewModelScope.launch (Dispatchers.IO){
                dashboardViewModel.logOutUser()
            }
        }

        btnNext.setOnClickListener {
            drawerController.navigateFromDashboardToFavourites()
        }
    }

    fun initRecyclerView(){
        categoriesPagingAdapter = CategoriesPagingAdapter(requireContext(), this)
        categoriesPagingAdapter.addCategoryClickListener(this)

        rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            adapter = categoriesPagingAdapter.withLoadStateFooter(
                footer = CategoryLoadStateAdapter(categoriesPagingAdapter)
            )
        }

        lifecycleScope.launch {
            dashboardViewModel.categories.collectLatest {
                categoriesPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            categoriesPagingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> showLoading()
                    is LoadState.Error -> {
                        val error = when {
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }

                        error?.let {
                            val message = if(it.error.message.isNullOrEmpty()) getString(R.string.error) else it.error.message!!
                            showError(message)
                        }
                    }
                    is LoadState.NotLoading -> {
                        showContent()
                    }
                }
            }
        }
    }


    fun showContent(){
        avlCategoryLoader.visibility = View.INVISIBLE
        rvCategories.visibility = View.VISIBLE
    }

    fun showError(message: String) {
        showErrorDialog(
            requireContext(),
            "Error",
            message,
            "Retry"
        ) {
            categoriesPagingAdapter.refresh()
        }
    }

    override fun onCategoryClicked(view: View, position: Int) {
       
    }

    private fun addObservers() {
        dashboardViewModel.showLoading.observe(viewLifecycleOwner) { showLoading() }
        dashboardViewModel.logout.observe(viewLifecycleOwner) { onLogOut() }
    }

    fun showLoading(){

    }

    fun onLogOut(){
        drawerController.popAll()
    }

    override fun onBackPressed() {
        activity?.moveTaskToBack(true)
    }
}