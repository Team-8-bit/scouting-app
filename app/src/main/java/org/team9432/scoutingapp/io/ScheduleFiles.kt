package org.team9432.scoutingapp.io

import org.team9432.scoutingapp.io.kindle.StorageInterface
import java.io.File

typealias MatchScoutingSchedule = Map<String, Map<Int, String>> // Match number to (scout ID to team)

object ScheduleFiles {
    fun getTeamToScout(matchNumber: String): String {
        return getMatchSchedule()[matchNumber]!![config.scoutID]!!
    }

    fun getMatches(): Map<String, String> {
        val scoutID = config.scoutID
        return getMatchSchedule().filterValues { it.containsKey(scoutID) }.mapValues { it.value[scoutID]!! }
    }

    private fun getMatchSchedule(): MatchScoutingSchedule {
        val lines = StorageInterface.readText("${config.eventID}/MatchSchedule.csv").lines().toCSV()

        val matches = mutableMapOf<String, Map<Int, String>>()
        for (i in 1 until lines.size) {
            val row = lines[i]
            val scoutsToTeams = mutableMapOf<Int, String>()

            for (j in 1..6) {
                scoutsToTeams[j] = row[j]
            }

            val matchNumber = row[0]
            matches[matchNumber] = scoutsToTeams
        }
        return matches
    }

    private fun List<String>.toCSV() = map { it.split(",") }
}