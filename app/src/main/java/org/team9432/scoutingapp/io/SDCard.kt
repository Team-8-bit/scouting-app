package org.team9432.scoutingapp.io

import java.io.File
import java.util.*
import java.util.regex.Pattern

object SDCard {
    private val cardID = getMagicNumbers()[0]

    val MAIN_FOLDER = File("/storage/$cardID")
    val CONFIG_FILE = File(MAIN_FOLDER, "config.json")

    val EVENT_DATA_DIR get() = File(MAIN_FOLDER, config.eventID)
    val MATCH_SCOUTING_DATA_FILE get() = File(EVENT_DATA_DIR, "MatchScoutingData.json")

    fun getMatchScheduleFile(): File {
        return files.first { it.name.contains(config.eventID, ignoreCase = true) && it.name.contains("Match", ignoreCase = true) }
    }

    fun getPitScheduleFile(): File {
        return files.first { it.name.contains(config.eventID, ignoreCase = true) && it.name.contains("Pit", ignoreCase = true) }
    }

    private fun getFile(name: String): File {
        return File(MAIN_FOLDER, name)
    }

    private val files: List<File>
        get() = MAIN_FOLDER.listFiles()!!.filter { it.isFile }.toList()

    private val fileNames: List<String>
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