package org.team9432.scoutingapp.io

import java.io.File

typealias SpreadSheet = List<List<String>>

object ScheduleFile {
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