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
    @InlineTextInputField(numberOnly = true) val matchNumber: String,
    @InlineTextInputField(numberOnly = true) val teamNumber: String,
    @InlineTextInputField(numberOnly = true) val scoutID: String,

    // This could potentially be done automatically based on team number + match number
    @CycleInputField("Red", "Blue") val alliance: String = "N/A",

    // Consider adding a field image input module for this
    @CycleInputField("Left", "Center", "Right") val autoStartingPosition: String = "N/A",

    @NumberInputField val autoScoredSpeaker: Int = 0,
    @NumberInputField val autoScoredAmp: Int = 0,
    @SwitchInputField val crossedAutoLine: Boolean = false,

    @SwitchInputField val pickupAutoNoteOne: Boolean = false,
    @SwitchInputField val pickupAutoNoteTwo: Boolean = false,
    @SwitchInputField val pickupAutoNoteThree: Boolean = false,
    @SwitchInputField val pickupAutoNoteFour: Boolean = false,
    @SwitchInputField val pickupAutoNoteFive: Boolean = false,

    @NumberInputField val teleSpeakerNotes: Int = 0,
    @NumberInputField val teleSpeakerNotesMissed: Int = 0,
    @NumberInputField val teleAmpNotes: Int = 0,
    @NumberInputField val teleAmpNotesMissed: Int = 0,

    @SwitchInputField val scoredTrap: Boolean = false,
    @SwitchInputField val spotlit: Boolean = false,
    @CycleInputField("No Attempt", "Parked", "Single", "Harmony x1", "Harmony x2") val endgame: String = "N/A",
    @CycleInputField("Excellent", "Acceptable", "Subpar", "Abysmal") val defenceQuality: String = "N/A",
    @CycleInputField("good", "ok", "🤨", "no") val drivingQuality: String = "N/A",
    @NumberInputField val penalties: Int = 0,
    @SwitchInputField val disabled: Boolean = false,
    @TextInputField val notes: String = "",
)