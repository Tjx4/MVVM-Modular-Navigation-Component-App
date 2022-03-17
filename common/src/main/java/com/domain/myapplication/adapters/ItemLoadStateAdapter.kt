package com.domain.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.domain.myapplication.R
import com.wang.avi.AVLoadingIndicatorView

class ItemLoadStateAdapter(
    private val pagingAdapter: BaseItemsPagingAdapter
) : LoadStateAdapter<ItemLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { pagingAdapter.retry() }
    }

    inner class NetworkStateItemViewHolder internal constructor(parent: ViewGroup, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.load_state_layout, parent, false)){
        private val progressBar: AVLoadingIndicatorView = itemView.findViewById(R.id.pb_progress)
        private val retry: Button = itemView.findViewById(R.id.b_retry)

        init {
            retry.setOnClickListener { retryCallback() }
        }

        fun bindTo(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
        }
    }

}