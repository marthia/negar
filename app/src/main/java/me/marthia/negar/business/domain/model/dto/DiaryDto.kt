package me.marthia.negar.business.domain.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaryDto(
    val diaryId: Long = 0,
    val color: String,
    val isTrashed: Boolean,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val textContent: String,
    val title: String,
    val userEditedTimestampUsec: Long,
    val createdTimestampUsec: Long,
) : Parcelable