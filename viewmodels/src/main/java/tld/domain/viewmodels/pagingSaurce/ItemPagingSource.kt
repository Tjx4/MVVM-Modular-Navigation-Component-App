package tld.domain.viewmodels.pagingSaurce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.domain.myapplication.constants.OUTLETS_PAGE_SIZE
import com.domain.myapplication.models.Item
import com.domain.repositories.items.ItemsRepository
import java.lang.NullPointerException

class ItemPagingSource(private val itemsRepository: ItemsRepository) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> = try {
        val loadPage = params.key ?: 0

        val items = itemsRepository.getRemoteItems()

        if (items == null) {
            LoadResult.Error(NullPointerException("Unknown error"))
        }
        else {
            val pages =  items.size / OUTLETS_PAGE_SIZE
            val currentPage = getCurrentPage(items, loadPage)
            currentPage?.forEach {
                val url = it.metaData
                if(!url.isNullOrEmpty()){
                    val image = itemsRepository.getItemImage(url)
                    it.image = image
                }
            }

            LoadResult.Page(
                data = currentPage,
                prevKey = null,
                nextKey = if (loadPage < pages) loadPage + 1 else null
            )

        }
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    fun getCurrentPage(response: List<Item>, loadPage: Int): List<Item>{
        val pageData = response.withIndex().groupBy { it.index / OUTLETS_PAGE_SIZE }.values.map { pp -> pp.map { it.value } }
        return pageData[loadPage]
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
       return 4
    }
}
