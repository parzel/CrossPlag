package de.parzelsec

import org.apache.tika.Tika
import java.io.File
import java.io.InputStream


object TextExtractor{

    var tika:Tika? = null

    init {
        tika = Tika()
    }

    fun extractText(file: File): String {
        val stream: InputStream = file.inputStream()
        return tika!!.parseToString(stream)
    }
}