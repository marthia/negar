package me.marthia.negar.utitlies

import me.marthia.negar.business.domain.model.file.DiaryJson

interface HTMLParser {

    fun execute(): DiaryJson?

}