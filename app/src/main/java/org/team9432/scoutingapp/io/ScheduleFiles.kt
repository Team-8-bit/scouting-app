package org.team9432.scoutingapp.io

import org.team9432.scoutingapp.io.data.*
import java.io.File

typealias SpreadSheet = List<List<String>>

object ScheduleFiles {
    fun getMatchScheduleFile(): File {
        return SDCard.files.first { it.name.contains(config.eventID, ignoreCase = true) && it.name.contains("Match", ignoreCase = true) }
    }

    fun getPitScheduleFile(): File {
        return SDCard.files.first { it.name.contains(config.eventID, ignoreCase = true) && it.name.contains("Pit", ignoreCase = true) }
    }

    fun getTeamToScout(matchNumber: Int): ScheduledTeamInMatch {
        return getMatchSchedule().scheduledMatches[matchNumber]!!.teams[config.scoutID]!!
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

    fun getMatches() = getMatchSchedule().scheduledMatches.filter { it.value.teams.containsKey(config.scoutID) }

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