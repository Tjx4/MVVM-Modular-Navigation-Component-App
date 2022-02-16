package com.domain.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.domain.myapplication.R
import com.domain.myapplication.models.Item

class ItemsPagingAdapter(private val context: Context) : PagingDataAdapter<Item, ItemsPagingAdapter.ItemsViewHolder>(ItemsComparator) {
    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.outlet_layout,
            parent,
            false
        )
        return ItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val outlet = getItem(position)
        holder.itemNameTv.text = outlet?.itemName

        //val url = ""
        //holder.previewRImv.loadImageFromUrl(context, url)
    }

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var itemNameTv = itemView.findViewById<TextView>(R.id.tvItemName)
        internal var previewRImv = itemView.findViewById<ImageView>(R.id.imgPreview)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            itemClickListener?.onItemClicked(view, adapterPosition)
        }
    }

    interface ItemClickListener {
        fun onItemClicked(view: View, position: Int)
    }

    fun addPairClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}

object ItemsComparator : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}