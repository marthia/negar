package me.marthia.negar.business.interactors

import me.marthia.negar.business.domain.mapper.asDto
import me.marthia.negar.business.domain.model.database.DiaryEntity
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

    suspend fun getNotes(
        limit: Int,
        offset: Int,
//             options: Map<String, String>
    ) = noteDao.getNotes(limit, offset)


    suspend fun updateContent(diaryId: Long, textContent: String, title: String) {
        noteDao.updateContent(diaryId, textContent, title)
    }
}