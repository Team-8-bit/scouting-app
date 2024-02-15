package org.team9432.scoutingapp.io.json

import kotlinx.serialization.Serializable
import org.team9432.scoutingapp.annotation.*
import org.team9432.scoutingapp.io.config

@Serializable
data class SuperscoutingFile(
    val matches: Map<Int, Map<String, MatchScoutingData>>, // Match number to (team number to data)
) {
    fun getMatchOrNull(matchNumber: Int, team: String) = matches[matchNumber]?.get(team)
    fun getMatchOrNew(matchNumber: Int, team: String) = getMatchOrNull(matchNumber, team) ?: SuperscoutingData(matchNumber.toString(), team, config.scoutID.toString())
}

@Serializable
@InputBase
data class SuperscoutingData(
    @InlineTextInputField(numberOnly = true) val matchNumber: String,
    @InlineTextInputField(numberOnly = true) val teamNumber: String,
    @InlineTextInputField(numberOnly = true) val scoutID: String,

    @InlineTextInputField(numberOnly = true) val scoutName: String = "",

    @CycleInputField("Red", "Blue") val alliance: String = "N/A",


    @CycleInputField("Amp", "Speaker", "Both") val primaryScoringLocation: String = "N/A",
    @CycleInputField("Source", "Ground", "Both") val primaryPickupLocation: String = "N/A",

    @NumberInputField val amplifierActivated: Int = 0,
    @SwitchInputField val coopActivated: Boolean = false,

    @CycleInputField("Primary Scorer", "Amp Scorer", "Speaker Scorer", "Defense") val robotRole: String = "N/A",
    @SwitchInputField val wasDefended: Boolean = true,

    @NumberInputField val cycles: Int = 0,

    @NumberInputField(max = 3, min = 1) val defenseQuality: Int = 2,
    @NumberInputField(max = 3, min = 1) val drivingQuality: Int = 2,

    @NumberInputField val penalties: Int = 0,
    @SwitchInputField val disabled: Boolean = false,
    @TextInputField val notes: String = "",
) {
    private val del = ";"
    val qrString
        get() = (
                matchNumber + del +
                        teamNumber + del +
                        alliance + del +
                        scoutName + del +

                        primaryScoringLocation + del +
                        primaryPickupLocation + del +

                        amplifierActivated + del +
                        coopActivated + del +

                        robotRole + del +
                        wasDefended + del +

                        cycles + del +
                        penalties + del +
                        defenseQuality + del +
                        drivingQuality + del +
                        disabled + del
                ).replace("true", "T").replace("false", "F") +
                notes.trim()
}
