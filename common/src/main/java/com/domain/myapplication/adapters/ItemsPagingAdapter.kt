package com.domain.myapplication.adapters

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.domain.myapplication.R
import com.domain.myapplication.enums.Links
import com.domain.myapplication.extensions.loadImageFromUrl
import com.domain.myapplication.models.Item

class ItemsPagingAdapter(private val context: Context, itemLayout: Int) : BaseItemsPagingAdapter(context, itemLayout) {
    private var itemVisibleListener: ItemVisibleListener? = null
    private var itemFavClickListener: ItemFavClickListener? = null

    override fun onBindViewHolder(baseItemViewHolder: BaseItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            val itemNameTv = baseItemViewHolder.itemView.findViewById<TextView>(R.id.tvItemName)
            itemNameTv.text = "${item?.title}"

            val previewRImv = baseItemViewHolder.itemView.findViewById<ImageView>(R.id.imgPreview)
            previewRImv.setImageResource(R.drawable.ic_normal_car)
            item?.image?.medium?.let{ url ->
                previewRImv.loadImageFromUrl(context, url, R.drawable.ic_normal_car)
            }

            item?.links?.get(Links.CardInfo.index)?.href?.let {
                itemVisibleListener?.onItemVisible(item, position)
            }

            val favImgb = baseItemViewHolder.itemView.findViewById<ImageView>(R.id.imgbFav)
            favImgb?.let {
                val tintColor = if(item.isFav) {R.color.fav } else {R.color.grey_background }
                val imgBtnFav = it as ImageView
                imgBtnFav?.setColorFilter(getColor(context, tintColor), android.graphics.PorterDuff.Mode.MULTIPLY)
                favImgb.setOnClickListener {
                    itemFavClickListener?.onFavClicked(item, position)
                }
            }

        }
    }

    interface ItemVisibleListener {
        fun onItemVisible(item: Item, position: Int)
    }

    fun addItemVisibleListener(itemVisibleListener: ItemVisibleListener) {
        this.itemVisibleListener = itemVisibleListener
    }

    interface ItemFavClickListener {
        fun onFavClicked(item: Item, position: Int)
    }

    fun addItemFavClickListener(itemFavClickListener: ItemFavClickListener) {
        this.itemFavClickListener = itemFavClickListener
    }

}
