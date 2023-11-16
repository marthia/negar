package me.marthia.negar.framework.presentation.note

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import com.developersancho.framework.base.mvi.BaseViewState
import com.developersancho.framework.base.mvi.MviViewModel
import com.developersancho.framework.extension.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.negar.business.domain.mapper.asEntity
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.domain.model.file.DiaryJson
import me.marthia.negar.business.interactors.GetNotes
import me.marthia.negar.business.interactors.NoteRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getNotes: GetNotes,
    private val noteRepository: NoteRepository,
) : MviViewModel<BaseViewState<NoteState>, NoteEvent>() {

    private val pagingConfig =
        PagingConfig(pageSize = 20, prefetchDistance = 2, initialLoadSize = 20)

    private val _importingProgress = MutableStateFlow(0.0f)
    val importingProgress = _importingProgress.asStateFlow()

    init {
        onTriggerEvent(NoteEvent.GetNotes)
    }

    override fun onTriggerEvent(event: NoteEvent) {
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
        safeLaunch {
            setState(BaseViewState.Data(NoteState(getNotes.invoke(GetNotes.Params(pagingConfig = pagingConfig)))))
        }
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
                json?.fromJson<DiaryJson>()?.let {
                    noteRepository.insertNote(it.asEntity())
                }
            }
        } catch (e: Exception) {
            Timber.e("SAVER", "Could not save item: $e")
        }
    }
}

