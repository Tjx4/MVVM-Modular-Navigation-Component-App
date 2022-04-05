package tld.domain.viewmodels.pagingSaurce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.domain.myapplication.constants.ITEM_PAGE_SIZE
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class ItemPagingSource(private val itemsRepository: ItemsRepository) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> = try {
        val loadPage = params.key ?: 0
        val pagedItems = itemsRepository.getPagedItems(loadPage)

        if (pagedItems == null) {
            LoadResult.Error(NullPointerException("No items received"))
        } else {
            val items = pagedItems.items ?: ArrayList()
            val pages = pagedItems?.pages ?: 0
            setFav(items)

            val prevKey = if (loadPage < 1) null else loadPage - 1
            val nextKey = if (loadPage < pages) loadPage + 1 else null

            LoadResult.Page(
                data = items,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    private suspend fun setFav(items: List<Item>){
        withContext(Dispatchers.IO) {
            items?.forEach { item ->
                val favourites = itemsRepository.getFavourites()
                item.isFav = favourites?.any{ it.id == item.id } ?: false
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
       return 0
    }
}
