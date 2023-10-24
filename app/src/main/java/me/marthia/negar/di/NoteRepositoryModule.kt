package me.marthia.negar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.marthia.negar.business.interactors.NoteRepository
import me.marthia.negar.framework.datasource.database.NoteDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NoteRepositoryModule {

    @Singleton
    @Provides
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository {
        return NoteRepository(noteDao = noteDao)
    }
}
