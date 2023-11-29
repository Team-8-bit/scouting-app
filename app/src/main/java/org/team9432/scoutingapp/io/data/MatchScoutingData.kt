package org.team9432.scoutingapp.io.data

import kotlinx.serialization.Serializable

@Serializable
data class MatchScoutingData(
    val matches: Map<Int, MatchScoutingMatchData>,
)

@Serializable
data class MatchScoutingMatchData(
    val matchNumber: Int,
    val team: String,
    val scoutID: Int,
    val data: ChargedUpMatchScoutingData?,
)

@Serializable
data class ChargedUpMatchScoutingData(
    val gpScoredHigh: Int,
    val gpScoredMid: Int,
    val gpScoredLow: Int,
    val engageAuto: AutoEngage,
)

@Serializable
enum class AutoEngage {
    SUCCESS, FAILED, NO_ATTEMPT
}