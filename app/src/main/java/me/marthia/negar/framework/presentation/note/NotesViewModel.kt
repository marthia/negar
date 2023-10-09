package me.marthia.negar.framework.presentation.note

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.interactors.NoteRepository
import me.marthia.negar.utitlies.HTMLParserImpl
import java.io.File
import java.nio.charset.Charset
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _notesList: MutableStateFlow<PagingData<DiaryDto>> =
        MutableStateFlow(value = PagingData.empty())
    val notesList: StateFlow<PagingData<DiaryDto>> = _notesList.asStateFlow()


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

    suspend fun getNote(id: Long): DiaryDto {
        return noteRepository.getNote(id)
    }

    private fun extractData(uri: Uri): String? {

        Log.i("ActivityResult0", "Uri: $uri")
        val inputStream = context.contentResolver?.openInputStream(uri)


        val htmlText = inputStream?.bufferedReader().use { it?.readText() }

//        Log.i("ActivityResult1", htmlText)
//
        val temp = htmlText
            ?.replace(
                "<style.*>.*</style>".toRegex(RegexOption.DOT_MATCHES_ALL),
                ""
            )
        val htmlWithoutBreaks = temp?.replace("<br>|</br>".toRegex(), "\n")

//        Log.i("ActivityResult2", htmlWithoutBreaks)

        inputStream?.close()

        return htmlWithoutBreaks
    }

    private suspend fun get(id: String): List<DiaryDto> {
        return noteRepository.find("%$id%")
    }

    private suspend fun getAll() {
        noteRepository.getNotes()
            .distinctUntilChanged()
            .collect {
//                _notesList.value = it
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

    fun saveFromUri(uri: Uri) {
        viewModelScope.launch {
            val htmlWithoutBrTags = extractData(uri)

            val file = withContext(Dispatchers.IO) {
                File.createTempFile("temp", ".html", context.cacheDir)
            }
            htmlWithoutBrTags?.let { file.writeText(it, Charset.forName("UTF8")) }


            val parser = HTMLParserImpl(file)
            val diary = parser.execute()
            diary?.let {
//                noteRepository.insertNote(it)
            }
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