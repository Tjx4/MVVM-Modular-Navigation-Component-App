package com.domain.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.domain.myapplication.models.Item

abstract class BaseItemsPagingAdapter(private val context: Context, private val itemLayout: Int) : PagingDataAdapter<Item, BaseItemsPagingAdapter.BaseItemViewHolder>(ItemsComparator) {
    private var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            itemLayout,
            parent,
            false
        )

        return BaseItemViewHolder(itemView)
    }

    inner class BaseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            getItem(position)?.let {
                itemClickListener?.onItemClicked(view, it, adapterPosition)
            }
        }
    }

    open interface ItemClickListener {
        fun onItemClicked(view: View, item: Item, position: Int)
    }

    fun addItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}

object ItemsComparator : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}