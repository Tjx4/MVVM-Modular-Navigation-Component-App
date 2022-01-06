package tld.domain.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.BaseFragment
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.extensions.vibratePhone
import kotlinx.android.synthetic.main.fragment_favourites.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.favourites.databinding.FragmentFavouritesBinding

class FavouritesFragment : BaseFragment() {
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
        imgBtnBack.setOnClickListener {
            vibratePhone(DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                drawerController.popBack()
            })
        }
    }
}