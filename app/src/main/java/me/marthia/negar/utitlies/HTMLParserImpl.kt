package me.marthia.negar.utitlies

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import me.marthia.negar.business.domain.model.dto.DiaryDto
import me.marthia.negar.business.domain.model.file.DiaryJson
import java.io.File

class HTMLParserImpl(
    private val file: File,
) : HTMLParser {
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(DiaryJson::class.java)

    /**
     * @param tag the attribute tag name to search in the HTML node list
     * @return the content of node based on the provided attribute tag
     */
    override fun execute(): DiaryJson? {
        return jsonAdapter.fromJson(file.readText())
    }
}