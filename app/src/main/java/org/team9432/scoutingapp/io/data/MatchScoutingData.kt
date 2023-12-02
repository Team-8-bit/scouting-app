package org.team9432.scoutingapp.io.data

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*

@Serializable
data class MatchScoutingData(
    val matches: Map<Int, Map<String, MatchScoutingMatchData>>, // Match number to (Team number to data)
)

@Serializable
data class MatchScoutingMatchData(
    val matchNumber: Int,
    val team: String,
    val scoutID: Int,
    val data: ChargedUpMatchScoutingData,
)

@Serializable
@DataScreen
data class ChargedUpMatchScoutingData(
    @SwitchInputField
    val autoMobility: Boolean = false,
    @CycleInputField("Success", "Fail", "Docked")
    val autoEngage: String = "No attempt",
    @NumberInputField
    val autoConesTop: Int = 0,
    @NumberInputField
    val autoConesMid: Int = 0,
    @NumberInputField
    val autoConesLow: Int = 0,
    @NumberInputField
    val autoCubesTop: Int = 0,
    @NumberInputField
    val autoCubesMid: Int = 0,
    @NumberInputField
    val autoCubesLow: Int = 0,
    @NumberInputField
    val teleConesTop: Int = 0,
    @NumberInputField
    val teleConesMid: Int = 0,
    @NumberInputField
    val teleConesLow: Int = 0,
    @NumberInputField
    val teleCubesTop: Int = 0,
    @NumberInputField
    val teleCubesMid: Int = 0,
    @NumberInputField
    val teleCubesLow: Int = 0,
    @SwitchInputField
    val defended: Boolean = false,
    @SwitchInputField
    val disabled: Boolean = false,
    @NumberInputField
    val penalties: Int = 0,
    @CycleInputField("Success", "Fail", "Docked")
    val teleEngage: String = "No attempt",
    @NumberInputField
    val conesDropped: Int = 0,
    @NumberInputField
    val cubesDropped: Int = 0,
    @NumberInputField
    val conesMissed: Int = 0,
    @NumberInputField
    val cubesMissed: Int = 0,
    @CycleInputField("Scoring", "Defence", "Shuttle")
    val robotRole: String = "N/A",
    @CycleInputField("Double", "Single", "Both")
    val substationPreference: String = "N/A",
    @CycleInputField("Cube", "Cone")
    val gamePiecePreference: String = "N/A",
    @CycleInputField("Excellent", "Acceptable", "Subpar", "Abysmal")
    val defenceQuality: String = "N/A",
    @CycleInputField("good", "ok-ish", "not ok", "no")
    val drivingQuality: String = "N/A",
    @CycleInputField("No", "First Pick", "Second Pick")
    val wouldPick: String = "N/A",
    @TextInputField
    val notes: String = "",
)