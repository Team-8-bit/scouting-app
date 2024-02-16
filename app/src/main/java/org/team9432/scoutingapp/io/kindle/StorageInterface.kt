package org.team9432.scoutingapp.io.kindle

import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest

interface StorageIO {
    fun writeText(filePath: String, string: String)
    fun readText(filePath: String): String

    fun deleteFile(filePath: String)
    fun checkExistence(filePath: String): Boolean
}

object StorageInterface: StorageIO {
    private val interfaces = listOf(SDCard)
    private const val ROOT_DIR = "scouting-app"

    override fun writeText(filePath: String, string: String) = interfaces.forEach { it.writeText("$ROOT_DIR/$filePath", string) }

    override fun deleteFile(filePath: String) {
        interfaces.forEach { it.deleteFile("$ROOT_DIR/$filePath") }
    }

    override fun checkExistence(filePath: String): Boolean {
        return interfaces.any { it.checkExistence("$ROOT_DIR/$filePath") }
    }

    override fun readText(filePath: String): String {
        val outputs = buildList {
            interfaces.forEach {
                add(it.readText("$ROOT_DIR/$filePath"))
            }
        }

        val hashes = outputs.map { it.md5() }.toSet()
        println(hashes.size)
        println(hashes.elementAt(0))
        if (hashes.size == 1) return outputs[0]

        throw IOException("Hashes do not match! $hashes")
    }

    private fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16).padStart(32, '0')
}