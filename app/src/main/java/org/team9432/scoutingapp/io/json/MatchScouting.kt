package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*
import org.team9432.scoutingapp.io.config

@Serializable
data class MatchScoutingFile(
    val matches: Map<Int, Map<String, MatchScoutingData>>, // Match number to (team number to data)
) {
    fun getMatchOrNull(matchNumber: Int, team: String) = matches[matchNumber]?.get(team)
    fun getMatchOrNew(matchNumber: Int, team: String) = getMatchOrNull(matchNumber, team) ?: MatchScoutingData(matchNumber.toString(), team, config.scoutID.toString())
}

@Serializable
@InputBase
data class MatchScoutingData(
    @InlineTextInputField(numberOnly = true)
    val matchNumber: String,
    @InlineTextInputField(numberOnly = true)
    val teamNumber: String,
    @InlineTextInputField(numberOnly = true)
    val scoutID: String,

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