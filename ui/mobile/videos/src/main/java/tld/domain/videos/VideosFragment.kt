package tld.domain.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.myapplication.adapters.ItemsPagingAdapter
import com.domain.myapplication.adapters.PPLoadStateAdapter
import com.domain.myapplication.base.TopNavigationFragment
import kotlinx.android.synthetic.main.fragment_videos.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.videos.databinding.FragmentVideosBinding
import tld.domain.viewmodels.VideosViewModel

class VideosFragment : TopNavigationFragment() , ItemsPagingAdapter.ItemClickListener{
    private lateinit var binding: FragmentVideosBinding
    private val videosViewModel: VideosViewModel by viewModel()
    private lateinit var itemsPagingAdapter: ItemsPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false)
        binding.lifecycleOwner = this
        binding.videosViewModel = videosViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    fun initRecyclerView(){
        itemsPagingAdapter = ItemsPagingAdapter(requireContext())
        itemsPagingAdapter.addPairClickListener(this)

        rvItems.apply {
            rvItems?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(false)
            adapter = itemsPagingAdapter.withLoadStateFooter(
                footer = PPLoadStateAdapter(itemsPagingAdapter)
            )
        }

        lifecycleScope.launch {
            videosViewModel.items.collectLatest {
                itemsPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            itemsPagingAdapter.loadStateFlow.collectLatest { loadState ->
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

    override fun onItemClicked(view: View, position: Int) {
        Toast.makeText(requireContext(), "${ videosViewModel.items} ${position}", Toast.LENGTH_SHORT).show()
    }

    fun showError(message: String) {
        val dd = message
    }

    fun showLoading(){
        avlClosestLoader.visibility = View.VISIBLE
        rvItems.visibility = View.INVISIBLE
    }

    fun showContent(){
        avlClosestLoader.visibility = View.INVISIBLE
        rvItems.visibility = View.VISIBLE
    }
}