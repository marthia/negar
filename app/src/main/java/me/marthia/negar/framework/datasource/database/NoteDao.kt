package me.marthia.negar.framework.datasource.database

import androidx.room.*
import me.marthia.negar.business.domain.model.database.DiaryEntity

@Dao
interface NoteDao {

    @Query("SELECT * FROM t_Diary ORDER BY userEditedTimestampUsec ASC LIMIT :limit OFFSET :offset ")
    suspend fun getNotes(limit: Int, offset: Int): List<DiaryEntity>

    @Query("SELECT * FROM t_Diary WHERE id = :id")
    suspend fun getNoteById(id: Long): DiaryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(noteViewModels: List<DiaryEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: DiaryEntity): Long

    @Update
    suspend fun updateNote(note: DiaryEntity)

    @Delete
    suspend fun deleteNote(note: DiaryEntity)

    @Delete
    suspend fun deleteNotes(list: Collection<DiaryEntity>)

    @Query("SELECT * FROM t_Diary WHERE textContent LIKE :keyword ORDER BY id COLLATE NOCASE ASC")
    suspend fun find(keyword: String): List<DiaryEntity>

    @Query("UPDATE t_Diary SET title=:title , textContent=:textContent WHERE id = :diaryId")
    suspend fun updateContent(diaryId: Long, textContent: String, title: String)


}
