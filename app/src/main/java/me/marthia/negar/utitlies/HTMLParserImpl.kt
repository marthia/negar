package me.marthia.negar.utitlies

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.File

class HTMLParserImpl(
    private val file: File,
) : HTMLParser {
    private val moshi: Moshi = Moshi.Builder().build()


    /**
     * @param tag the attribute tag name to search in the HTML node list
     * @return the content of node based on the provided attribute tag
     */
    override fun execute(): Diary? {
        val jsonAdapter: JsonAdapter<Diary> = moshi.adapter(Diary::class.java)
        return jsonAdapter.fromJson(file.readText())
    }
}