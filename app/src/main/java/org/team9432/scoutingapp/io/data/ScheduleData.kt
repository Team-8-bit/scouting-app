package org.team9432.scoutingapp.io.data

data class MatchScoutingSchedule(
    val scheduledMatches: Map<Int, Map<Int, String>>, // Match number to (scout ID to team)
)

data class PitScoutingTeams(
    val teams: List<String>,
)