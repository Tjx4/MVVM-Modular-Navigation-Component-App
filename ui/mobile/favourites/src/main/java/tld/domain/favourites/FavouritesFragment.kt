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
    }

    private fun addObservers() {
        favouritesViewModel.favItems.observe(viewLifecycleOwner, { onFavItemsSet(it) })
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