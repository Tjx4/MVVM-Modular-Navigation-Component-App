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
import com.domain.myapplication.adapters.*
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.domain.myapplication.helpers.showErrorDialog
import com.domain.myapplication.models.Item
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tld.domain.viewmodels.DashboardViewModel

class DashboardFragment : TopNavigationFragment(), CategoriesPagingAdapter.CategoryClickListener, CategoryItemsPagingAdapter.CategoryItemVisibleListener, BaseItemsPagingAdapter.ItemClickListener {
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
        avlCategoryLoader.visibility = View.INVISIBLE
        rvCategories.visibility = View.INVISIBLE

        showErrorDialog(
            requireContext(),
            "Error",
            message,
            "Retry"
        ) {
            avlCategoryLoader.visibility = View.VISIBLE
            categoriesPagingAdapter.refresh()
        }
    }

    override fun onCategoryClicked(view: View, position: Int) {
       
    }

    private fun addObservers() {
        dashboardViewModel.showLoading.observe(viewLifecycleOwner) { showLoading() }
        dashboardViewModel.logout.observe(viewLifecycleOwner) { onLogOut() }
        dashboardViewModel.currentCategoryAndItem.observe(viewLifecycleOwner) { onCategoryItemUpdated(it) }
    }

    fun showLoading(){

    }

    fun onLogOut(){
        drawerController.popAll()
    }

    override fun onBackPressed() {
        activity?.moveTaskToBack(true)
    }

    private fun onCategoryItemUpdated(categoryAndItem: Pair<Int, Int>) {
        val categoryPosition = categoryAndItem.first
        val itemPosition = categoryAndItem.second
        categoriesPagingAdapter.updateCurrentCategory(categoryPosition, itemPosition)
    }

    override fun onCategoryItemVisible(item: Item, categoryPosition: Int, itemPosition: Int) {
        dashboardViewModel.checkAndFetchCategoryImage(item, categoryPosition, itemPosition)
    }

    override fun onItemClicked(view: View, item: Item, position: Int) {
        drawerController.navigateFromDashboardToViewItem(item)
    }
}