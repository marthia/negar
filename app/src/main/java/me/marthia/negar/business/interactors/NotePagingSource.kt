package me.marthia.negar.business.interactors

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.marthia.negar.business.domain.mapper.asDto
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.framework.datasource.database.NoteDao

class NotePagingSource(
    private val dao: NoteDao
) : PagingSource<Int, DiaryDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiaryDto> {
        val page = params.key ?: 1

        return try {
            val entities = dao.getNotes(params.loadSize, page * params.loadSize)

            LoadResult.Page(
                data = entities.map { it.asDto() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
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