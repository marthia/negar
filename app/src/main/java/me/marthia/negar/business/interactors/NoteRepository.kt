package me.marthia.negar.business.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.marthia.negar.business.domain.model.database.DiaryEntity
import me.marthia.negar.framework.datasource.database.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun getNote(noteId: Long) = noteDao.getNoteById(noteId)

    suspend fun insertNote(note: DiaryEntity): Long {
        return noteDao.insertNote(note)
    }

    fun updateNote(scope: CoroutineScope, note: DiaryEntity) {
        scope.launch { noteDao.updateNote(note) }
    }

    suspend fun deleteNotes(notes: Collection<DiaryEntity>? = null, note: DiaryEntity? = null) {
        notes?.let { noteDao.deleteNotes(it) } ?: noteDao.deleteNote(note!!)
    }

    suspend fun find(keyword: String) = noteDao.find(keyword)

    fun getNotes(): Flow<PagingData<DiaryEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                NotePagingSource(noteDao)
            }
        ).flow
    }
}