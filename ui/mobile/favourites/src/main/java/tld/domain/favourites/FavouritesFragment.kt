package tld.domain.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.domain.myapplication.adapters.FavouritesAdapter
import com.domain.myapplication.base.BaseFragment
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.extensions.runWhenReady
import com.domain.myapplication.extensions.vibratePhone
import com.domain.myapplication.models.FavItem
import kotlinx.android.synthetic.main.fragment_favourites.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.favourites.databinding.FragmentFavouritesBinding
import tld.domain.viewmodels.FavouritesViewModel

class FavouritesFragment : BaseFragment(), FavouritesAdapter.FavouritesClickListener {
    private lateinit var binding: FragmentFavouritesBinding
    private val favouritesViewModel: FavouritesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
        binding.lifecycleOwner = this
        binding.favouritesViewModel = favouritesViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()

        imgBtnBack.setOnClickListener {
            vibratePhone(DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                drawerController.popBack()
            })
        }

        btnAdd.setOnClickListener {
            vibratePhone(DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                favouritesViewModel.addFavItems()
                it.visibility = View.INVISIBLE
                btnAddMany.visibility = View.INVISIBLE
            })
        }

        btnAddMany.setOnClickListener {
            vibratePhone(DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                favouritesViewModel.addManyItems()
                it.visibility = View.INVISIBLE
                btnAdd.visibility = View.INVISIBLE
            })
        }
    }

    private fun addObservers() {
        favouritesViewModel.showLoading.observe(viewLifecycleOwner, { onShowLoading() })
        favouritesViewModel.favItems.observe(viewLifecycleOwner, { onFavItemsSet(it) })
        favouritesViewModel.noItems.observe(viewLifecycleOwner, { onNoItems() })
    }

    private fun onShowLoading(){
        tvNoItems.visibility = View.INVISIBLE
        btnAdd.visibility = View.INVISIBLE
        btnAddMany.visibility = View.INVISIBLE
        rvFavourites.visibility = View.INVISIBLE
        avLoader.visibility = View.VISIBLE
    }

    private fun onFavItemsSet(favItems: List<FavItem>){
        val favouritesLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        favouritesLayoutManager.initialPrefetchItemCount = favItems.size
        rvFavourites?.layoutManager = favouritesLayoutManager
        var favouritesAdapter = FavouritesAdapter(requireContext(), favItems)
        favouritesAdapter.setFavouritesClickListener(this)
        rvFavourites?.adapter = favouritesAdapter

        rvFavourites.runWhenReady {
            tvNoItems.visibility = View.INVISIBLE
            btnAdd.visibility = View.INVISIBLE
            btnAddMany.visibility = View.INVISIBLE
            rvFavourites.visibility = View.VISIBLE
            avLoader.visibility = View.INVISIBLE
        }

    }

    private fun onNoItems(){
        tvNoItems.visibility = View.VISIBLE
        btnAdd.visibility = View.VISIBLE
        btnAddMany.visibility = View.VISIBLE
        rvFavourites.visibility = View.INVISIBLE
        avLoader.visibility = View.INVISIBLE
    }

    override fun onFavouritesClick(view: View, position: Int) {
        favouritesViewModel.favItems.value?.get(position)?.let {
            viewFavItem(it)
        }
    }

    private fun viewFavItem(favItem: FavItem){
        drawerController.navigateFromFavouritesToViewFavourites(favItem)
    }
}