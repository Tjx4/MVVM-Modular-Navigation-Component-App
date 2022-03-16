package com.domain.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.domain.myapplication.R
import com.domain.myapplication.extensions.loadImageFromUrl
import com.domain.myapplication.models.Item

class ItemsPagingAdapter(private val context: Context, private val itemLayout: Int) : PagingDataAdapter<Item, ItemsPagingAdapter.ItemsViewHolder>(ItemsComparator) {
    private var itemClickListener: ItemClickListener? = null
    private var itemVisibleListener: ItemVisibleListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            itemLayout,
            parent,
            false
        )

        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.itemNameTv.text = "${item?.itemName}"

            holder.previewRImv.setImageResource(R.drawable.ic_normal_car)
            item?.image?.medium?.let{ url ->
                holder.previewRImv.loadImageFromUrl(context, url, R.drawable.ic_normal_car)
            }

            item?.metaData?.let {
                itemVisibleListener?.onItemVisible(item, position)
            }

            val tintColor = if(item.isFav) {R.color.fav } else {R.color.grey_background }
            holder.favImgb?.let {
                val imgBtnFav = it as ImageView
                imgBtnFav?.setColorFilter(getColor(context, tintColor), android.graphics.PorterDuff.Mode.MULTIPLY)
                holder.favImgb.setOnClickListener {
                    itemClickListener?.onFavClicked(item, position)
                }
            }

        }
    }

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var itemNameTv = itemView.findViewById<TextView>(R.id.tvItemName)
        internal var previewRImv = itemView.findViewById<ImageView>(R.id.imgPreview)
        internal var favImgb = itemView.findViewById<ImageView>(R.id.imgbFav)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            getItem(position)?.let {
                itemClickListener?.onItemClicked(view, it, adapterPosition)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClicked(view: View, item: Item, position: Int)
        fun onFavClicked(item: Item, position: Int)
    }

    fun addItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface ItemVisibleListener {
        fun onItemVisible(item: Item, position: Int)
    }

    fun addItemVisibleListener(itemVisibleListener: ItemVisibleListener) {
        this.itemVisibleListener = itemVisibleListener
    }
}

object ItemsComparator : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}