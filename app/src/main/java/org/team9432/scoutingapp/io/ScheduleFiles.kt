package org.team9432.scoutingapp.io

import org.team9432.scoutingapp.io.data.*
import java.io.File

typealias SpreadSheet = List<List<String>>

object ScheduleFiles {
    fun getMatchScheduleFile(eventID: String): File {
        return SDCard.files.first { it.name.contains(eventID, ignoreCase = true) && it.name.contains("Match", ignoreCase = true) }
    }

    fun getPitScheduleFile(eventID: String): File {
        return SDCard.files.first { it.name.contains(eventID, ignoreCase = true) && it.name.contains("Pit", ignoreCase = true) }
    }

    fun getTeamToScout(eventID: String, matchNumber: Int, scoutID: Int): ScheduledTeamInMatch {
        return getMatchSchedule(eventID).scheduledMatches[matchNumber]!!.teams[scoutID]!!
    }

    fun getMatchSchedule(eventID: String): MatchScoutingSchedule {
        return parseSpreadsheet(getMatchScheduleFile(eventID).readLines()).toMatchScoutingSheet()
    }

    fun getPitSchedule(eventID: String): PitScoutingTeams {
        return parseSpreadsheet(getPitScheduleFile(eventID).readLines()).toPitScoutingSheet()
    }

    private fun parseSpreadsheet(file: List<String>): SpreadSheet {
        return file.map { it.split(",") }
    }

    fun getMatches(scoutID: Int, eventID: String) = getMatchSchedule(eventID).scheduledMatches.filter { it.value.teams.containsKey(scoutID) }

    private fun SpreadSheet.toMatchScoutingSheet(): MatchScoutingSchedule {
        val scheduledMatches = mutableMapOf<Int, ScheduledMatch>()
        for (i in 1 until this.size) {
            val row = this[i]
            val teams = mutableMapOf<Int, ScheduledTeamInMatch>()

            for (j in 1..6) {
                teams[j] = ScheduledTeamInMatch(
                    teamNumber = row[j].split("; ")[0],
                    alliance = row[j].split("; ")[1],
                )
            }

            val matchNumber = row[0].toInt()
            scheduledMatches[matchNumber] = ScheduledMatch(number = matchNumber, teams = teams)
        }
        return MatchScoutingSchedule(scheduledMatches)
    }

    private fun SpreadSheet.toPitScoutingSheet(): PitScoutingTeams {
        val teams = mutableListOf<String>()
        for (i in 1 until this.size) {
            teams.add(this[i][0])
        }
        return PitScoutingTeams(teams)
    }
}