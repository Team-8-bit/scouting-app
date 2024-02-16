package org.team9432.scoutingapp.io

import org.team9432.scoutingapp.io.json.Schedule
import org.team9432.scoutingapp.io.json.ScoutingSchedule
import org.team9432.scoutingapp.io.json.json
import org.team9432.scoutingapp.io.kindle.StorageInterface

object ScheduleInterface {
    fun getTeamToScout(matchNumber: String): String {
        return getMatchSchedule().matches.getValue(matchNumber)
    }

    fun getMatches(): Map<String, String> {
        return getMatchSchedule().matches
    }

    private fun getMatchSchedule(): ScoutingSchedule {
        val schedule = json.decodeFromString<Schedule>(StorageInterface.readText("${config.eventID}/schedule.json"))

        return schedule.scoutingSchedule.getValue(config.scoutID)
    }
}