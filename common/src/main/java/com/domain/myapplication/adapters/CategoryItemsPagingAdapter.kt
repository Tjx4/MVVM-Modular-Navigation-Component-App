package com.domain.myapplication.adapters

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.domain.myapplication.R
import com.domain.myapplication.enums.Links
import com.domain.myapplication.extensions.loadImageFromUrl
import com.domain.myapplication.models.Item

class CategoryItemsPagingAdapter(private val context: Context, itemLayout: Int, private val categoryPosition: Int) : BaseItemsPagingAdapter(context, itemLayout) {
    private var categoryItemVisibleListener: CategoryItemVisibleListener? = null

    override fun onBindViewHolder(baseItemViewHolder: BaseItemViewHolder, position: Int) {
        getItem(position)?.let { item ->
            val itemNameTv = baseItemViewHolder.itemView.findViewById<TextView>(R.id.tvItemName)
            itemNameTv.text = "${item?.itemName}"

            val previewRImv = baseItemViewHolder.itemView.findViewById<ImageView>(R.id.imgPreview)
            previewRImv.setImageResource(R.drawable.ic_normal_car)
            item?.image?.medium?.let{ url ->
                previewRImv.loadImageFromUrl(context, url, R.drawable.ic_normal_car)
            }

            item?.links?.get(Links.CardInfo.index)?.href?.let {
                categoryItemVisibleListener?.onCategoryItemVisible(item, categoryPosition, position)
            }

        }
    }

    interface CategoryItemVisibleListener {
        fun onCategoryItemVisible(item: Item, categoryPosition: Int, itemPosition: Int)
    }

    fun addItemVisibleListener(categoryItemVisibleListener: CategoryItemVisibleListener) {
        this.categoryItemVisibleListener = categoryItemVisibleListener
    }

}