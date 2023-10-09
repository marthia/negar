package me.marthia.negar.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "db_Notes"
    }
}