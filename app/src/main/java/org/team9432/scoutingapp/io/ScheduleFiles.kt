package org.team9432.scoutingapp.io

import org.team9432.scoutingapp.io.data.MatchScoutingSchedule
import org.team9432.scoutingapp.io.data.PitScoutingTeams
import java.io.File

typealias SpreadSheet = List<List<String>>

object ScheduleFiles {
    fun getMatchScheduleFile(): File {
        return SDCard.files.first { it.name.contains(config.eventID, ignoreCase = true) && it.name.contains("Match", ignoreCase = true) }
    }

    fun getPitScheduleFile(): File {
        return SDCard.files.first { it.name.contains(config.eventID, ignoreCase = true) && it.name.contains("Pit", ignoreCase = true) }
    }

    fun getTeamToScout(matchNumber: Int): String {
        return getMatchSchedule().scheduledMatches[matchNumber]!![config.scoutID]!!
    }

    fun getMatchSchedule(): MatchScoutingSchedule {
        return parseSpreadsheet(getMatchScheduleFile().readLines()).toMatchScoutingSheet()
    }

    fun getPitSchedule(): PitScoutingTeams {
        return parseSpreadsheet(getPitScheduleFile().readLines()).toPitScoutingSheet()
    }

    private fun parseSpreadsheet(file: List<String>): SpreadSheet {
        return file.map { it.split(",") }
    }

    fun getMatches(): Map<Int, String> {
        val scoutID = config.scoutID
        return getMatchSchedule().scheduledMatches.filterValues { it.containsKey(scoutID) }.mapValues { it.value[scoutID]!! }
    }

    private fun SpreadSheet.toMatchScoutingSheet(): MatchScoutingSchedule {
        val matches = mutableMapOf<Int, Map<Int, String>>()
        for (i in 1 until this.size) {
            val row = this[i]
            val scoutsToTeams = mutableMapOf<Int, String>()

            for (j in 1..6) {
                scoutsToTeams[j] = row[j]
            }

            val matchNumber = row[0].toInt()
            matches[matchNumber] = scoutsToTeams
        }
        return MatchScoutingSchedule(matches)
    }

    private fun SpreadSheet.toPitScoutingSheet(): PitScoutingTeams {
        val teams = mutableListOf<String>()
        for (i in 1 until this.size) {
            teams.add(this[i][0])
        }
        return PitScoutingTeams(teams)
    }
}