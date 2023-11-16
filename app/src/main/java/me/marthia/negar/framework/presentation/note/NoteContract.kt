package me.marthia.negar.framework.presentation.note

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.marthia.negar.business.domain.model.dto.DiaryDto

data class NoteState(
    val data: Flow<PagingData<DiaryDto>>
)


sealed class NoteEvent {
    data object GetNotes : NoteEvent()
    data class Search(val query: String) : NoteEvent()
}