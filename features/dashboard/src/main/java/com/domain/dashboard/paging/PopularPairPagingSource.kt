package com.domain.dashboard.paging


import com.domain.core.persistance.room.tables.popularPair.PopularPairTable
import com.domain.repositories.forex.ForexRepository
import java.lang.NullPointerException

class PopularPairPagingSource(private val forexRepository: ForexRepository) : PagingSource<Int, PopularPairTable>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularPairTable> = try {
        val loadPage = params.key ?: 0

        val result = forexRepository.getPopularCurrencyPairs(
            `apiKey` = API_KEY
        )

        if (result == null || result?.error != null) {
            val error = result?.error
            LoadResult.Error(NullPointerException(error?.info ?: "Unknown error"))
        }
        else {
            val response = ArrayList<PopularPairTable>()
            result?.currencies?.forEach {
                val popularPairTable = PopularPairTable(
                        `pair` = it.key,
                        `fullName` = it.value
                    )
                response.add(popularPairTable)
            }

            val pages = response.size / PP_PAGE_SIZE
            val currentPage = getCurrentPage(response, loadPage, PP_PAGE_SIZE)

            LoadResult.Page(
                data = currentPage,
                prevKey = null,
                nextKey = if (loadPage < pages) loadPage + 1 else null
            )
        }
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    private fun getCurrentPage(response: List<PopularPairTable>, pageIndex: Int, pageSize: Int): List<PopularPairTable>{
        val pageData = response.withIndex().groupBy { it.index / pageSize }.values.map { pp -> pp.map { it.value } }
        return pageData[pageIndex]
    }

}