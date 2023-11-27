package org.team9432.scoutingapp.io

import android.content.ContentResolver
import android.net.Uri
import java.io.InputStreamReader

typealias SpreadSheet = List<List<String>>

var resolver: ContentResolver? = null

fun spreadsheetFromURI(uri: Uri): SpreadSheet {
    return readUri(uri).map { it.split(",") }
}

fun readUri(uri: Uri): List<String> {
    val inputStream = resolver?.openInputStream(uri)
    val reader = InputStreamReader(inputStream)
    return reader.readLines()
}