package me.marthia.negar.business.domain.model.file

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class DiaryJson(
    @Json(name = "color") val color: String?,
    @Json(name = "isTrashed") val isTrashed: Boolean?,
    @Json(name = "isPinned") val isPinned: Boolean?,
    @Json(name = "isArchived") val isArchived: Boolean?,
    @Json(name = "textContent") val textContent: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "userEditedTimestampUsec") val userEditedTimestampUsec: Long,
    @Json(name = "createdTimestampUsec") val createdTimestampUsec: Long,
)