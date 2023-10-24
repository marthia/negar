package me.marthia.negar.business.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.marthia.negar.business.domain.mapper.asDto
import me.marthia.negar.business.domain.model.database.DiaryEntity
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.framework.datasource.database.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun getNote(noteId: Long) = noteDao.getNoteById(noteId)?.asDto()

    suspend fun insertNote(note: DiaryEntity): Long {
        return noteDao.insertNote(note)
    }

    suspend fun updateNote(note: DiaryEntity) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNotes(notes: Collection<DiaryEntity>? = null, note: DiaryEntity? = null) {
        notes?.let { noteDao.deleteNotes(it) } ?: noteDao.deleteNote(note!!)
    }

    suspend fun find(keyword: String) = noteDao.find(keyword).map { it.asDto() }

    fun getNotes(): Flow<PagingData<DiaryDto>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2, initialLoadSize = 20),
            pagingSourceFactory = {
                NotePagingSource(noteDao)
            }
        ).flow
    }
}