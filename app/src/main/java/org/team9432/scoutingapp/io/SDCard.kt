package org.team9432.scoutingapp.io

import java.io.File
import java.util.*
import java.util.regex.Pattern

typealias SpreadSheet = List<List<String>>

object SDCard {
    private val cardID = getMagicNumbers()[0]
    private val storageDir = File("/storage/$cardID")

    fun getFile(predicate: (String) -> Boolean): File? {
        val potentialFiles = storageDir.listFiles()?.filter { it.isFile } ?: return null
        return potentialFiles.firstOrNull { predicate(it.name) }
    }

    fun getMatchSchedule(file: File): MatchScoutingSheet {
        return parseSpreadsheet(file.readLines()).toMatchScoutingSheet()
    }

    fun getPitSchedule(file: File): PitScoutingSheet {
        return parseSpreadsheet(file.readLines()).toPitScoutingSheet()
    }

    private fun parseSpreadsheet(file: List<String>): SpreadSheet {
        return file.map { it.split(",") }
    }

    private fun SpreadSheet.toMatchScoutingSheet(): MatchScoutingSheet {
        val matches = mutableListOf<Match>()
        for (i in 1 until this.size) {
            val row = this[i]
            val teams = mutableListOf<Team>()

            for (j in 1..6) {
                teams.add(
                    Team(
                        scoutID = j,
                        teamNumber = row[j].split("; ")[0],
                        alliance = row[j].split("; ")[1],
                    )
                )
            }

            matches.add(Match(number = row[0].toInt(), teams = teams))
        }
        return MatchScoutingSheet(matches)
    }

    private fun SpreadSheet.toPitScoutingSheet(): PitScoutingSheet {
        val teams = mutableListOf<String>()
        for (i in 1 until this.size) {
            teams.add(this[i][0])
        }
        return PitScoutingSheet(teams)
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

data class MatchScoutingSheet(
    val matches: List<Match>,
)

data class Match(
    val number: Int,
    val teams: List<Team>,
)

data class Team(
    val scoutID: Int,
    val alliance: String,
    val teamNumber: String,
)

data class PitScoutingSheet(
    val teams: List<String>,
)