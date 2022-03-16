package tld.domain.viewmodels.pagingSaurce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.domain.myapplication.constants.CATEGORY_PAGE_SIZE
import com.domain.myapplication.models.ItemCategory
import com.domain.repositories.items.ItemsRepository
import java.lang.NullPointerException

class ItemCategoryPagingSource(private val itemsRepository: ItemsRepository) : PagingSource<Int, ItemCategory>() {
    private var categories: List<ItemCategory>? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemCategory> = try {
        val loadPage = params.key ?: 0

        if(categories.isNullOrEmpty()){
            categories = itemsRepository.getCategorizedItems()
        }

        if (categories == null) {
            LoadResult.Error(NullPointerException("No items received"))
        } else {
            val currentPage = getCurrentPage(categories!!, loadPage)
            val mainPages = categories!!.size / CATEGORY_PAGE_SIZE
            val additionalPages = if((categories!!.size % CATEGORY_PAGE_SIZE) > 0) 1 else 0
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

    private fun getCurrentPage(response: List<ItemCategory>, loadPage: Int): List<ItemCategory>{
        val pageData = response.withIndex().groupBy {
            it.index / CATEGORY_PAGE_SIZE
        }.values.map { items ->
            items.map {
                it.value
            }
        }

        return pageData[loadPage]
    }

    override fun getRefreshKey(state: PagingState<Int, ItemCategory>): Int? {
        return 0
    }
}
