package me.marthia.negar.framework.presentation.note.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.interactors.NoteRepository
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    @DiaryIdArg private val diaryId: Long,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _noteItem = MutableStateFlow<DiaryDto?>(null)
    val noteItem = _noteItem.asStateFlow()

    init {
        getNote(diaryId)
    }

    private fun getNote(id: Long) {
        viewModelScope.launch {
            _noteItem.value = noteRepository.getNote(id)
        }
    }
}