package me.marthia.negar.business.domain.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_Diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val diaryId: Long = 0,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "isTrashed")
    val isTrashed: Boolean,
    @ColumnInfo(name = "isPinned")
    val isPinned: Boolean,
    @ColumnInfo(name = "isArchived")
    val isArchived: Boolean,
    @ColumnInfo(name = "textContent")
    val textContent: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "userEditedTimestampUsec")
    val userEditedTimestampUsec: Long,
    @ColumnInfo(name = "createdTimestampUsec")
    val createdTimestampUsec: Long,
)