package me.marthia.negar.business.domain.mapper

import me.marthia.negar.business.domain.model.database.DiaryEntity
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.domain.model.file.DiaryJson

fun DiaryEntity.asDto() = DiaryDto(
    diaryId = diaryId,
    color = color,
    isTrashed = isTrashed,
    isPinned = isPinned,
    isArchived = isArchived,
    textContent = textContent,
    title = title,
    userEditedTimestampUsec = userEditedTimestampUsec,
    createdTimestampUsec = createdTimestampUsec,
)

fun DiaryJson.asEntity() = DiaryEntity(
    color = color ?: "",
    isTrashed = isTrashed ?: false,
    isPinned = isPinned ?: false,
    isArchived = isArchived ?: false,
    textContent = textContent ?: "",
    title = title ?: "",
    userEditedTimestampUsec = userEditedTimestampUsec,
    createdTimestampUsec = createdTimestampUsec,
)

fun DiaryDto.fromDto(): DiaryEntity = DiaryEntity(
    diaryId = diaryId,
    color = color,
    isTrashed = isTrashed,
    isPinned = isPinned,
    isArchived = isArchived,
    textContent = textContent,
    title = title,
    userEditedTimestampUsec = userEditedTimestampUsec,
    createdTimestampUsec = createdTimestampUsec
)