package tld.domain.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.BaseFragment
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.extensions.loadImageFromUrl
import com.domain.myapplication.extensions.vibratePhone
import com.domain.myapplication.models.FavItem
import kotlinx.android.synthetic.main.fragment_view_favourite.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.favourites.databinding.FragmentViewFavouriteBinding
import tld.domain.viewmodels.ViewFavouriteViewModel

class ViewFavouriteFragment : BaseFragment() {
    private lateinit var binding: FragmentViewFavouriteBinding
    private val viewFavouriteViewModel: ViewFavouriteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_favourite, container, false)
        binding.lifecycleOwner = this
        binding.viewFavouriteViewModel = viewFavouriteViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<FavItem>("favItem")?.let {
            viewFavouriteViewModel.setFunItem(it)

            it.image?.xl?.let { url ->
                imgFavouriteItemIcon.loadImageFromUrl(requireContext(), url, com.domain.myapplication.R.drawable.ic_img_dark)
            }
        }

        imgBtnBack.setOnClickListener {
            vibratePhone(DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                drawerController.popBack()
            })
        }
    }
}