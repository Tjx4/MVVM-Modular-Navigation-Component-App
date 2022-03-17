package com.domain.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.domain.myapplication.R
import com.domain.myapplication.base.fragments.BaseFragment
import com.domain.myapplication.constants.ITEM_PAGE_SIZE
import com.domain.myapplication.models.Item
import com.domain.myapplication.models.ItemCategory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class CategoriesPagingAdapter(private val context: Context, val fragment: BaseFragment) : PagingDataAdapter<ItemCategory, CategoriesPagingAdapter.CategoriesViewHolder>(CategoriesComparator) {
    private var categoryClickListener: CategoryClickListener? = null
    //private var categoriesItems: List<Item>? = null
    var subAdapters = ArrayList<CategoryItemsPagingAdapter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.category_layout,
            parent,
            false
        )

        return CategoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.itemTitleTv.text = "${item?.title}"

            item.items?.let { categoriesItems ->
                //this.categoriesItems = categoriesItems

                val categoryItemsPagingAdapter = CategoryItemsPagingAdapter(context, R.layout.basic_item_layout, position)
                categoryItemsPagingAdapter.addItemClickListener(fragment as BaseItemsPagingAdapter.ItemClickListener)
                categoryItemsPagingAdapter.addItemVisibleListener(fragment as CategoryItemsPagingAdapter.CategoryItemVisibleListener)

                holder.categoryItemsRv.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(false)
                    adapter = categoryItemsPagingAdapter.withLoadStateFooter(
                        footer = ItemLoadStateAdapter(categoryItemsPagingAdapter)
                    )
                }

                val items = Pager(config = PagingConfig(pageSize = ITEM_PAGE_SIZE)) {
                    CategoryItemPagingSource(categoriesItems)
                }.flow.cachedIn(fragment.lifecycleScope)

                fragment.lifecycleScope.launch {
                    items.collectLatest {
                        categoryItemsPagingAdapter.submitData(it)
                    }
                }

                initChildeRecyclerView(categoryItemsPagingAdapter, holder)
                subAdapters.add(categoryItemsPagingAdapter)
            }

        }
    }

    fun updateCurrentCategory(categoryPosition: Int, itemPosition: Int){
        //Todo Remove once back issue is sorted
        if(!subAdapters.isNullOrEmpty()){
            subAdapters[categoryPosition]?.notifyItemChanged(itemPosition)
        }
    }

    fun initChildeRecyclerView(categoryItemsPagingAdapter: CategoryItemsPagingAdapter, holder: CategoriesViewHolder){
        fragment.lifecycleScope.launch {
            categoryItemsPagingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        holder.noItemsTv.visibility = View.GONE
                        holder.categoryItemsRv.visibility = View.GONE
                    }
                    is LoadState.Error -> {
                        val error = when {
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }

                        error?.let {
                            val message = if(it.error.message.isNullOrEmpty()) context.getString(R.string.error) else it.error.message

                            holder.noItemsTv.visibility = View.VISIBLE
                            holder.categoryItemsRv.visibility = View.GONE
                        }
                    }
                    is LoadState.NotLoading -> {
                        holder.noItemsTv.visibility = View.GONE
                        holder.categoryItemsRv.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var itemTitleTv = itemView.findViewById<TextView>(R.id.tvItemTitle)
        internal var categoryItemsRv = itemView.findViewById<RecyclerView>(R.id.rvCategoryItems)
        internal var noItemsTv = itemView.findViewById<TextView>(R.id.tvNoItems)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            categoryClickListener?.onCategoryClicked(view, adapterPosition)
        }
    }

    inner class CategoryItemPagingSource(private var items: List<Item>?) : PagingSource<Int, Item>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> = try {
            val loadPage = params.key ?: 0

            if (items == null) {
                LoadResult.Error(NullPointerException("No items received"))
            } else {
                val currentPage = getCurrentPage(items!!, loadPage)
                val mainPages = items!!.size / ITEM_PAGE_SIZE
                val additionalPages = if((items!!.size % ITEM_PAGE_SIZE) > 0) 1 else 0
                val pages = mainPages + additionalPages

                val prevKey = if (loadPage < 1) null else loadPage - 1
                val nextKey = if (loadPage < (pages - 1)) loadPage + 1 else null

                LoadResult.Page(
                    data = currentPage,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

        private fun getCurrentPage(response: List<Item>, loadPage: Int): List<Item>{
            val pageData = response.withIndex().groupBy {
                it.index / ITEM_PAGE_SIZE
            }.values.map { items ->
                items.map {
                    it.value
                }
            }

            return pageData[loadPage]
        }

        override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
            return 0
        }
    }

    interface CategoryClickListener {
        fun onCategoryClicked(view: View, position: Int)
    }

    fun addCategoryClickListener(categoryClickListener: CategoryClickListener) {
        this.categoryClickListener = categoryClickListener
    }

}

object CategoriesComparator : DiffUtil.ItemCallback<ItemCategory>() {
    override fun areItemsTheSame(oldItem: ItemCategory, newItem: ItemCategory) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ItemCategory, newItem: ItemCategory) = oldItem == newItem
}