package org.team9432.scoutingapp.io.data

data class MatchScoutingSchedule(
    val scheduledMatches: List<ScheduledMatch>,
)

data class ScheduledMatch(
    val number: Int,
    val teams: Map<Int, ScheduledTeamInMatch>,
)

data class ScheduledTeamInMatch(
    val alliance: String,
    val teamNumber: String,
)

data class PitScoutingTeams(
    val teams: List<String>,
)