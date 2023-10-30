package me.marthia.negar.framework.presentation.note

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.negar.business.domain.mapper.asEntity
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.domain.model.file.DiaryJson
import me.marthia.negar.business.interactors.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val noteRepository: NoteRepository
) : ViewModel() {

    var noteList: Flow<PagingData<DiaryDto>> =
        MutableStateFlow(value = PagingData.empty())
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(DiaryJson::class.java)

    private val _importingProgress = MutableStateFlow(0.0f)
    val importingProgress = _importingProgress.asStateFlow()

    init {
        onEvent(NoteEvent.GetNotes)
    }

    fun onEvent(event: NoteEvent) {
        viewModelScope.launch {
            when (event) {
                is NoteEvent.GetNotes -> getAll()
                is NoteEvent.Search -> get(event.query)
            }
        }
    }

    private suspend fun get(id: String): List<DiaryDto> {
        return noteRepository.find("%$id%")
    }

    private fun getAll() {
        viewModelScope.launch {
            noteList = noteRepository.getNotes()
        }
    }


    private fun save(element: DiaryDto) {
        TODO("Not yet implemented")
    }

    private fun delete(element: DiaryDto) {
        viewModelScope.launch {
//            noteRepository.deleteNotes(
//                note = element
//            )
        }
    }

    private fun edit(element: DiaryDto) {
        TODO("Not yet implemented")
    }

    fun saveFromUri(uri: List<Uri>) {
        uri.forEachIndexed { index, item ->
            saveFromUri(item)
            _importingProgress.value = index / (uri.size - 1.0f)
        }
    }

    fun saveFromUri(uri: Uri) {
        try {
            viewModelScope.launch {
                val json = context.contentResolver?.openInputStream(uri)?.bufferedReader()
                    .use { it?.readText() }
                jsonAdapter.fromJson(json)?.let {
                    noteRepository.insertNote(it.asEntity())
                }
            }
        } catch (e: Exception) {
            Log.e("SAVER", "Could not save item: $e")
        }
    }

    fun delete(elements: Collection<DiaryDto>) {
        viewModelScope.launch {
//            noteRepository.deleteNotes(notes = elements)
        }
    }

}

sealed class NoteEvent {
    data object GetNotes : NoteEvent()
    data class Search(val query: String) : NoteEvent()
}