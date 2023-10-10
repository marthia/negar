package me.marthia.negar.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.marthia.negar.business.domain.model.database.DiaryEntity


@Database(entities = [DiaryEntity::class], version =2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "db_Notes"
    }
}