package tld.domain.viewitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import com.domain.myapplication.base.fragments.SubNavigationFragment
import com.domain.myapplication.constants.DURATION_SHORT
import com.domain.myapplication.constants.FAV_ITEM
import com.domain.myapplication.extensions.blinkView
import com.domain.myapplication.extensions.loadImageFromUrl
import com.domain.myapplication.extensions.vibratePhone
import com.domain.myapplication.models.Item
import kotlinx.android.synthetic.main.fragment_view_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import tld.domain.viewitem.databinding.FragmentViewItemBinding
import tld.domain.viewmodels.ViewItemViewModel

class ViewItemFragment : SubNavigationFragment() {
    private lateinit var binding: FragmentViewItemBinding
    private val viewItemViewModel: ViewItemViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_item, container, false)
        binding.lifecycleOwner = this
        binding.viewFavouriteViewModel = viewItemViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Item>(FAV_ITEM)?.let {
            viewItemViewModel.setFunItem(it)

            it.image?.xl?.let { url ->
                imgFavouriteItemIcon.loadImageFromUrl(requireContext(), url, com.domain.myapplication.R.drawable.ic_img_dark)
            }
        }

        imgBtnBack.setOnClickListener {
            vibratePhone(DURATION_SHORT)
            it.blinkView(0.6f, 1.0f, 100, 2, Animation.ABSOLUTE, 0, {
                onBackPressed()
            })
        }
    }
}