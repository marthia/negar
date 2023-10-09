package me.marthia.negar.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.marthia.negar.framework.datasource.database.AppDatabase
import me.marthia.negar.framework.datasource.database.NoteDao
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {


    @Singleton
    @Provides
    fun provideNotesDatabase(@ApplicationContext context: Context): AppDatabase {

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }
}