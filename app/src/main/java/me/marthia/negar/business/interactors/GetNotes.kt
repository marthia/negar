package me.marthia.negar.business.interactors

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.developersancho.framework.usecase.FlowPagingUseCase
import kotlinx.coroutines.flow.Flow
import me.marthia.negar.business.domain.model.dto.DiaryDto
import javax.inject.Inject

class GetNotes @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: NoteRepository
) : FlowPagingUseCase<GetNotes.Params, DiaryDto>() {

    data class Params(
        val pagingConfig: PagingConfig,
//        val options: Map<String, String>
    )

    override fun execute(params: Params): Flow<PagingData<DiaryDto>> {
        return Pager(
            config = params.pagingConfig,
            pagingSourceFactory = { NotePagingSource(repository /*params.options*/) }
        ).flow
    }
}