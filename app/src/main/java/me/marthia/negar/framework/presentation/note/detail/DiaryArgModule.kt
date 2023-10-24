package me.marthia.negar.framework.presentation.note.detail

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
annotation class DiaryIdArg

@Module
@InstallIn(ViewModelComponent::class)
object DiaryArgModule {

    @Provides
    @DiaryIdArg
    @ViewModelScoped
    fun provideGreetingNameNavArg(savedStateHandle: SavedStateHandle): Long {
        return savedStateHandle.get<Long>("id") ?: -1L
    }
}