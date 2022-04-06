package com.domain.myapplication.adapters.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.domain.myapplication.R
import com.domain.myapplication.extensions.loadImageFromUrl
import com.domain.myapplication.models.Item
import com.makeramen.roundedimageview.RoundedImageView

class FavouritesAdapter(private val context: Context, private val favouritesItems: List<Item>) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var favouritesClickListener: FavouritesClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.fav_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favouriteItem = favouritesItems[position]

        holder.nameTv.text = favouriteItem.title
        favouriteItem.image?.thumbNail?.let {
            holder.iconImgB.loadImageFromUrl(context, it, R.drawable.ic_img_dark)
        }
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var nameTv: TextView = itemView.findViewById(R.id.tvName)
        internal var iconImgB: RoundedImageView = itemView.findViewById(R.id.imgFavouriteItemIcon)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            favouritesClickListener?.onFavouritesClick(view, adapterPosition)
        }
    }

    internal fun getItem(id: Int): Item? {
        return favouritesItems[id]
    }

    fun setFavouritesClickListener(favouritesClickListener: FavouritesClickListener) {
        this.favouritesClickListener = favouritesClickListener
    }

    interface FavouritesClickListener {
        fun onFavouritesClick(view: View, position: Int)
    }

    override fun getItemCount(): Int {
        return favouritesItems.size
    }
}