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
    @SwitchInputField(false)
    val autoMobility: Boolean = false,
    @CycleInputField("No attempt", "Success", "Fail", "Docked")
    val autoEngage: String = "No attempt",
    @NumberInputField(0)
    val autoConesTop: Int = 0,
    @NumberInputField(0)
    val autoConesMid: Int = 0,
    @NumberInputField(0)
    val autoConesLow: Int = 0,
    @NumberInputField(0)
    val autoCubesTop: Int = 0,
    @NumberInputField(0)
    val autoCubesMid: Int = 0,
    @NumberInputField(0)
    val autoCubesLow: Int = 0,
    @NumberInputField(0)
    val teleConesTop: Int = 0,
    @NumberInputField(0)
    val teleConesMid: Int = 0,
    @NumberInputField(0)
    val teleConesLow: Int = 0,
    @NumberInputField(0)
    val teleCubesTop: Int = 0,
    @NumberInputField(0)
    val teleCubesMid: Int = 0,
    @NumberInputField(0)
    val teleCubesLow: Int = 0,
    @SwitchInputField(false)
    val defended: Boolean = false,
    @SwitchInputField(false)
    val disabled: Boolean = false,
    @NumberInputField(0)
    val penalties: Int = 0,
    @CycleInputField("No attempt", "Success", "Fail", "Docked")
    val teleEngage: String = "No attempt",
    @NumberInputField(0)
    val conesDropped: Int = 0,
    @NumberInputField(0)
    val cubesDropped: Int = 0,
    @NumberInputField(0)
    val conesMissed: Int = 0,
    @NumberInputField(0)
    val cubesMissed: Int = 0,
    @CycleInputField("N/A", "Scoring", "Defence", "Shuttle")
    val robotRole: String = "N/A",
    @CycleInputField("N/A", "Double", "Single", "Both")
    val substationPreference: String = "N/A",
    @CycleInputField("N/A", "Cube", "Cone")
    val gamePiecePreference: String = "N/A",
    @CycleInputField("N/A", "Excellent", "Acceptable", "Subpar", "Abysmal")
    val defenceQuality: String = "N/A",
    @CycleInputField("N/A", "good", "ok-ish", "not ok", "no")
    val drivingQuality: String = "N/A",
    @CycleInputField("N/A", "No", "First Pick", "Second Pick")
    val wouldPick: String = "N/A",
    @TextInputField("")
    val notes: String = "",
)