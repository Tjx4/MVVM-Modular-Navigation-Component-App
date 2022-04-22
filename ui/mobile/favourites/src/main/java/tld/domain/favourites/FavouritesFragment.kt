package tld.domain.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.adapters.favourites.FavouritesAdapter
import com.domain.myapplication.base.fragments.SubNavigationFragment
import com.domain.myapplication.base.fragments.TopNavigationFragment
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.extensions.runWhenReady
import com.domain.myapplication.helpers.vibratePhone
import com.domain.myapplication.models.Item
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.favourites.databinding.FragmentFavouritesBinding
import tld.domain.viewmodels.FavouritesViewModel

class FavouritesFragment : SubNavigationFragment(), FavouritesAdapter.FavouritesClickListener {
    private lateinit var binding: FragmentFavouritesBinding
    private val favouritesViewModel: FavouritesViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        drawerController.showBottomNav()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouritesViewModel.showLoading.observe(this) { onShowLoading() }
        favouritesViewModel.items.observe(this) { onFavItemsSet(it) }
        favouritesViewModel.noItems.observe(this) { onNoItems() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
        binding.lifecycleOwner = this
        binding.favouritesViewModel = favouritesViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouritesViewModel.viewModelScope.launch(Dispatchers.IO) {
            favouritesViewModel.getFavourites()
        }

        imgBtnBack.setOnClickListener {
            vibratePhone(requireContext(), DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                onBackPressed()
            })
        }

        btnClear.setOnClickListener {
            favouritesViewModel.clearItems()
        }
    }

    private fun onShowLoading(){
        tvNoItems.visibility = View.INVISIBLE
        rvFavourites.visibility = View.INVISIBLE
        btnClear.visibility = View.INVISIBLE
        avLoader.visibility = View.VISIBLE
    }

    private fun onFavItemsSet(items: List<Item>){
        val favouritesLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        favouritesLayoutManager.initialPrefetchItemCount = items.size
        rvFavourites?.layoutManager = favouritesLayoutManager
        var favouritesAdapter = FavouritesAdapter(requireContext(), items)
        favouritesAdapter.setFavouritesClickListener(this)
        rvFavourites?.adapter = favouritesAdapter

        rvFavourites.runWhenReady {
            tvNoItems.visibility = View.INVISIBLE
            rvFavourites.visibility = View.VISIBLE
            btnClear.visibility = View.VISIBLE
            avLoader.visibility = View.INVISIBLE
        }

    }

    private fun onNoItems(){
        tvNoItems.visibility = View.VISIBLE
        rvFavourites.visibility = View.INVISIBLE
        btnClear.visibility = View.INVISIBLE
        avLoader.visibility = View.INVISIBLE
    }

    override fun onFavouritesClick(view: View, position: Int) {
        favouritesViewModel.items.value?.get(position)?.let {
            viewFavItem(it)
        }
    }

    private fun viewFavItem(item: Item){
        drawerController.navigateFromFavouritesToViewItem(item)
    }
}