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
    val data: ChargedUpMatchScoutingData,
)

@Serializable
data class ChargedUpMatchScoutingData(
    val autoMobility: Boolean = false,
    val autoEngage: String = "No attempt",
    val autoConesTop: Int = 0,
    val autoConesMid: Int = 0,
    val autoConesLow: Int = 0,
    val autoCubesTop: Int = 0,
    val autoCubesMid: Int = 0,
    val autoCubesLow: Int = 0,
    val teleConesTop: Int = 0,
    val teleConesMid: Int = 0,
    val teleConesLow: Int = 0,
    val teleCubesTop: Int = 0,
    val teleCubesMid: Int = 0,
    val teleCubesLow: Int = 0,
    val defended: Boolean = false,
    val disabled: Boolean = false,
    val penalties: Int = 0,
    val teleEngage: String = "No attempt",
    val conesDropped: Int = 0,
    val cubesDropped: Int = 0,
    val conesMissed: Int = 0,
    val cubesMissed: Int = 0,
    val robotRole: String = "N/A",
    val substationPreference: String = "N/A",
    val gamePiecePreference: String = "N/A",
    val defenceQuality: String = "N/A",
    val drivingQuality: String = "N/A",
    val wouldPick: String = "N/A",
    val notes: String = "",
)