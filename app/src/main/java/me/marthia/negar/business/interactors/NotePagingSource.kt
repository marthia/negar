package me.marthia.negar.business.interactors

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.marthia.negar.business.domain.mapper.asDto
import me.marthia.negar.business.domain.model.dto.DiaryDto
import timber.log.Timber

class NotePagingSource(
    private val repo: NoteRepository
) : PagingSource<Int, DiaryDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiaryDto> {
        val page = params.key ?: 1

        return try {
            val entities = repo.getNotes(params.loadSize, page * params.loadSize)

            LoadResult.Page(
                data = entities.map { it.asDto() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Timber.e("Error Loading Notes ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DiaryDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}