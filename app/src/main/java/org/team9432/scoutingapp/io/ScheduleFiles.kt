package org.team9432.scoutingapp.io

import org.team9432.scoutingapp.io.data.MatchScoutingSchedule
import org.team9432.scoutingapp.io.data.PitScoutingTeams
import java.io.File

object ScheduleFiles {
    fun getTeamToScout(matchNumber: Int): String {
        return getMatchSchedule()[matchNumber]!![config.scoutID]!!
    }

    fun getMatches(): Map<Int, String> {
        val scoutID = config.scoutID
        return getMatchSchedule().filterValues { it.containsKey(scoutID) }.mapValues { it.value[scoutID]!! }
    }

    private fun getMatchSchedule(): MatchScoutingSchedule {
        val lines = SDCard.getMatchScheduleFile().readCSV()

        val matches = mutableMapOf<Int, Map<Int, String>>()
        for (i in 1 until lines.size) {
            val row = lines[i]
            val scoutsToTeams = mutableMapOf<Int, String>()

            for (j in 1..6) {
                scoutsToTeams[j] = row[j]
            }

            val matchNumber = row[0].toInt()
            matches[matchNumber] = scoutsToTeams
        }
        return matches
    }

    private fun getPitSchedule(): PitScoutingTeams {
        val lines = SDCard.getPitScheduleFile().readCSV()

        val teams = mutableListOf<String>()
        for (i in 1 until lines.size) {
            teams.add(lines[i][0])
        }
        return teams
    }

    private fun File.readCSV() = readLines().map { it.split(",") }
}