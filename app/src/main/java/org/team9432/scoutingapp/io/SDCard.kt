package org.team9432.scoutingapp.io

import java.io.File
import java.util.*
import java.util.regex.Pattern

object SDCard {
    private val cardID = getMagicNumbers()[0]

    private val MAIN_FOLDER = File("/storage/$cardID")
    val CONFIG_FILE = File(MAIN_FOLDER, "config.json")

    fun getFile(name: String): File {
        return File(MAIN_FOLDER, name)
    }

    val files: List<File>
        get() = MAIN_FOLDER.listFiles()!!.filter { it.isFile }.toList()

    val fileNames: List<String>
        get() = files.map { it.name }

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