package me.marthia.negar.framework.presentation.note.detail

import androidx.lifecycle.viewModelScope
import com.developersancho.framework.base.mvvm.MvvmViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.marthia.negar.business.domain.mapper.fromDto
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.interactors.NoteRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel @Inject constructor(
    @DiaryIdArg private val diaryId: Long,
    private val noteRepository: NoteRepository
) : MvvmViewModel() {

    private val _noteItem = MutableStateFlow<DiaryDto?>(null)
    val noteItem = _noteItem.asStateFlow()
    private var insertedId: Long? = null

    init {
        getNote(diaryId)
    }

    private fun getNote(id: Long) {
        viewModelScope.launch {
            _noteItem.value = noteRepository.getNote(id)
        }
    }

    fun saveNewNote(title: String, content: String, item: DiaryDto?) {
        val currentTimeMillis = System.currentTimeMillis()
        safeLaunch {
            if (item == null) {
                if (insertedId == null) {
                    insertedId = noteRepository.insertNote(
                        DiaryDto(
                            color = "",
                            isTrashed = false,
                            isArchived = false,
                            isPinned = false,
                            textContent = content,
                            title = title,
                            userEditedTimestampUsec = currentTimeMillis,
                            createdTimestampUsec = currentTimeMillis
                        ).fromDto()
                    )
                    Timber.d("inserted $insertedId")

                } else {
                    noteRepository.updateContent(
                        diaryId = insertedId!!,
                        textContent = content,
                        title = title,
                    )
                }
            } else
                noteRepository.updateNote(
                    DiaryDto(
                        diaryId = item.diaryId,
                        color = item.color,
                        isTrashed = item.isTrashed,
                        isArchived = item.isArchived,
                        isPinned = item.isPinned,
                        textContent = content,
                        title = title,
                        userEditedTimestampUsec = currentTimeMillis,
                        createdTimestampUsec = item.createdTimestampUsec
                    ).fromDto()
                )
        }
    }
}