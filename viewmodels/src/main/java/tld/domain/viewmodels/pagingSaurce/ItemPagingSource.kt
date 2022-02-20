package tld.domain.viewmodels.pagingSaurce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.domain.myapplication.constants.PAGE_SIZE
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tld.domain.viewmodels.FavouritesViewModel
import tld.domain.viewmodels.R
import java.lang.NullPointerException

class ItemPagingSource(private val itemsRepository: ItemsRepository) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> = try {
        val loadPage = params.key ?: 0

        val items = itemsRepository.getRemoteItems()

        if (items == null) {
            LoadResult.Error(NullPointerException("Unknown error"))
        }
        else {
            val currentPage = getCurrentPage(items, loadPage).first
            val pages = getCurrentPage(items, loadPage).second

            currentPage?.forEach { item ->
                item.apply {
                    isFav = isFave(item)
                    tintColor = if(isFav) R.color.gold else R.color.grey_background
                }
            }

            /*
            currentPage?.forEach {
                val url = it.metaData
                if(!url.isNullOrEmpty()){
                    val image = itemsRepository.getItemImage(url)
                    it.image = image
                }
            }
            */

            val prevKey = if (loadPage < 1) null  else loadPage - 1
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

   private suspend fun isFave(item: Item) : Boolean {
        return withContext(Dispatchers.IO){
            itemsRepository.getFavourites()?.contains(item) ?: false
        }
    }

   private fun getCurrentPage(response: List<Item>, loadPage: Int): Pair<List<Item>, Int>{
        val pageData = response.withIndex().groupBy {
            it.index / PAGE_SIZE
        }.values.map { items ->
            items.map {
                it.value
            }
        }

        return Pair(pageData[loadPage], pageData.size)
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
       return 0
    }
}
