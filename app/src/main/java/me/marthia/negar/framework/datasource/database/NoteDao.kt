package me.marthia.negar.framework.datasource.database

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM t_Notes  ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getNotes(limit: Int, offset: Int): List<Note>

    @Query("SELECT * FROM t_Notes WHERE id = :id")
    suspend fun getNoteById(id: Long): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(noteViewModels: List<Note>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Diary): Long

    @Update
    suspend fun updateNote(note: Diary)

    @Delete
    suspend fun deleteNote(note: Diary)

    @Delete
    suspend fun deleteNotes(list: Collection<Diary>)

    @Query("SELECT * FROM t_Notes WHERE body LIKE :keyword ORDER BY id COLLATE NOCASE ASC")
    suspend fun find(keyword: String): List<Diary>


}
