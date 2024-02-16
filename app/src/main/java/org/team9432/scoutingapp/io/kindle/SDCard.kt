package org.team9432.scoutingapp.io.kindle

import java.io.File
import java.util.*
import java.util.regex.Pattern

object SDCard: StorageIO {
    private val cardID = getMagicNumbers()[0]

    private val ROOT_FOLDER = File("/storage/$cardID")

    override fun writeText(filePath: String, string: String) {
        val file = getFile(filePath)
        if (!file.exists()) {
            file.createNewFile()
            file.mkdirs()
        }
        file.writeText(string)
    }

    override fun readText(filePath: String): String {
        val file = getFile(filePath)
        return if (file.exists()) file.readText() else ""
    }

    override fun deleteFile(filePath: String) {
        val file = getFile(filePath, createIfMissing = false)
        if (file.exists()) file.delete()
    }

    override fun checkExistence(filePath: String) = getFile(filePath, createIfMissing = false).exists()

    private fun getFile(filePath: String, createIfMissing: Boolean = true): File {
        val dir = filePath.dropLastWhile { it != '/' }.dropLast(1)
        val name = filePath.takeLastWhile { it != '/' }

        if (dir.isNotBlank()) {
            val dirFile = File(ROOT_FOLDER, dir)
            val file = File(dirFile, name)
            if (createIfMissing) {
                if (!dirFile.exists()) dirFile.mkdirs()
                if (!file.exists()) file.createNewFile()
            }
            return file
        } else {
            val file = File(ROOT_FOLDER, name)
            if (createIfMissing && !file.exists()) file.createNewFile()
            return file
        }
    }

    private fun getMagicNumbers(): List<String> {
        val magicNumbers = mutableListOf<String>()
        val paths = mutableSetOf<String>()

        val regex = Pattern.compile("""\d\d\d\d-\d\d\d\d""")

        val mounts = File("/proc/mounts").readLines()
        for (mount in mounts) {
            if (mount.contains("vfat") || mount.contains("/mnt")) {
                val tokens = StringTokenizer(mount, " ")
                tokens.nextToken() //device
                val mountPoint = tokens.nextToken() //mount point
                if (paths.contains(mountPoint)) continue
                tokens.nextToken() //file system

                if (mount.contains("/dev/block/vold")) {
                    if (!mount.contains("/mnt/secure")
                        && !mount.contains("/mnt/asec")
                        && !mount.contains("/mnt/obb")
                        && !mount.contains("/dev/mapper")
                        && !mount.contains("tmpfs")) {
                        paths.add(mountPoint)
                        val matcher = regex.matcher(mountPoint)
                        if (matcher.find()) {
                            magicNumbers.add(matcher.group())
                        }
                    }
                }
            }
        }

        return magicNumbers
    }
}